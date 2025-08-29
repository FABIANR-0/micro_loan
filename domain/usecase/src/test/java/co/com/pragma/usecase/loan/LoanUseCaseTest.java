package co.com.pragma.usecase.loan;

import co.com.pragma.model.gateways.LoggerGateway;
import co.com.pragma.model.gateways.TransactionalGateway;
import co.com.pragma.model.gateways.UserGateway;
import co.com.pragma.model.loanapplication.LoanApplication;
import co.com.pragma.model.loanapplication.exception.ResourceNotFoundException;
import co.com.pragma.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.pragma.model.loanstatus.gateways.LoanStatusRepository;
import co.com.pragma.model.loantype.gateways.LoanTypeRepository;
import co.com.pragma.model.user.User;
import co.com.pragma.model.loantype.LoanType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanUseCaseTest {

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @Mock
    private LoanTypeRepository loanTypeRepository;

    @Mock
    private LoanStatusRepository loanStatusRepository;

    @Mock
    private UserGateway userGateway;

    @Mock
    private LoggerGateway logger;

    @Mock
    private TransactionalGateway transactional;

    private LoanUseCase loanUseCase;
    private LoanApplication loanApplication;
    private User user;
    private LoanType loanType;

    @BeforeEach
    void setUp() {
        loanUseCase = new LoanUseCase(
                loanApplicationRepository,
                loanTypeRepository,
                loanStatusRepository,
                userGateway,
                logger,
                transactional
        );

        // Datos de prueba
        loanApplication = LoanApplication.builder()
                .dni("12345678")
                .loanTypeId(1L)
                .amount(BigDecimal.valueOf(10000))
                .term(12)
                .build();

        user = User.builder()
                .dni("12345678")
                .email("test@test.com")
                .name("Test User")
                .build();

        loanType = LoanType.builder()
                .loanTypeId(1L)
                .name("Préstamo Personal")
                .build();
    }

    @Test
    void createLoan_WhenAllValidationsPass_ShouldCreateLoanSuccessfully() {
        // Given
        Long statusId = 1L;
        LoanApplication savedLoan = loanApplication.toBuilder()
                .applicationId(1L)
                .email(user.getEmail())
                .statusId(statusId)
                .applicationDate(LocalDate.now())
                .build();

        when(userGateway.getUserByDni("12345678")).thenReturn(Mono.just(user));
        when(loanTypeRepository.findLoanTypeById(1L)).thenReturn(Mono.just(loanType));
        when(loanStatusRepository.findStatusByName("PENDIENTE")).thenReturn(Mono.just(statusId));
        when(loanApplicationRepository.saveLoanApplication(any(LoanApplication.class))).thenReturn(Mono.just(savedLoan));

        // When & Then
        StepVerifier.create(loanUseCase.createLoan(loanApplication))
                .expectNext(savedLoan)
                .verifyComplete();

        verify(userGateway).getUserByDni("12345678");
        verify(loanTypeRepository).findLoanTypeById(1L);
        verify(loanStatusRepository).findStatusByName("PENDIENTE");
        verify(loanApplicationRepository).saveLoanApplication(any(LoanApplication.class));
        verify(logger, atLeastOnce()).info(anyString(), any());
    }

    @Test
    void createLoan_WhenPendingStatusNotFound_ShouldThrowResourceNotFoundException() {
        // Given
        when(userGateway.getUserByDni("12345678")).thenReturn(Mono.just(user));
        when(loanTypeRepository.findLoanTypeById(1L)).thenReturn(Mono.just(loanType));
        when(loanStatusRepository.findStatusByName("PENDIENTE")).thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(loanUseCase.createLoan(loanApplication))
                .expectError(ResourceNotFoundException.class)
                .verify();

        verify(userGateway).getUserByDni("12345678");
        verify(loanTypeRepository).findLoanTypeById(1L);
        verify(loanStatusRepository).findStatusByName("PENDIENTE");
        verifyNoInteractions(loanApplicationRepository);
    }

    @Test
    void createLoan_WhenRepositorySaveFails_ShouldPropagateError() {
        // Given
        Long statusId = 1L;
        RuntimeException repositoryException = new RuntimeException("Database error");

        when(userGateway.getUserByDni("12345678")).thenReturn(Mono.just(user));
        when(loanTypeRepository.findLoanTypeById(1L)).thenReturn(Mono.just(loanType));
        when(loanStatusRepository.findStatusByName("PENDIENTE")).thenReturn(Mono.just(statusId));
        when(loanApplicationRepository.saveLoanApplication(any(LoanApplication.class)))
                .thenReturn(Mono.error(repositoryException));

        // When & Then
        StepVerifier.create(loanUseCase.createLoan(loanApplication))
                .expectError(RuntimeException.class)
                .verify();

        verify(logger).error("Error al guardar la solicitud");
    }

    @Test
    void createLoan_ShouldSetEmailFromUser() {
        // Given
        Long statusId = 1L;
        LoanApplication savedLoan = loanApplication.toBuilder()
                .applicationId(1L)
                .email(user.getEmail())
                .statusId(statusId)
                .applicationDate(LocalDate.now())
                .build();

        when(userGateway.getUserByDni("12345678")).thenReturn(Mono.just(user));
        when(loanTypeRepository.findLoanTypeById(1L)).thenReturn(Mono.just(loanType));
        when(loanStatusRepository.findStatusByName("PENDIENTE")).thenReturn(Mono.just(statusId));
        when(loanApplicationRepository.saveLoanApplication(any(LoanApplication.class))).thenReturn(Mono.just(savedLoan));

        // When & Then
        StepVerifier.create(loanUseCase.createLoan(loanApplication))
                .expectNextMatches(result -> result.getEmail().equals(user.getEmail()))
                .verifyComplete();
    }

    @Test
    void createLoan_ShouldSetCurrentDateAsApplicationDate() {
        // Given
        Long statusId = 1L;
        LocalDate today = LocalDate.now();
        LoanApplication savedLoan = loanApplication.toBuilder()
                .applicationId(1L)
                .email(user.getEmail())
                .statusId(statusId)
                .applicationDate(today)
                .build();

        when(userGateway.getUserByDni("12345678")).thenReturn(Mono.just(user));
        when(loanTypeRepository.findLoanTypeById(1L)).thenReturn(Mono.just(loanType));
        when(loanStatusRepository.findStatusByName("PENDIENTE")).thenReturn(Mono.just(statusId));
        when(loanApplicationRepository.saveLoanApplication(any(LoanApplication.class))).thenReturn(Mono.just(savedLoan));

        // When & Then
        StepVerifier.create(loanUseCase.createLoan(loanApplication))
                .expectNextMatches(result -> result.getApplicationDate().equals(today))
                .verifyComplete();
    }
}