package co.com.pragma.usecase.loan;

import co.com.pragma.model.gateways.LoggerGateway;
import co.com.pragma.model.gateways.TransactionalGateway;
import co.com.pragma.model.gateways.UserGateway;
import co.com.pragma.model.loanapplication.LoanApplication;
import co.com.pragma.model.loanapplication.exception.ResourceNotFoundException;
import co.com.pragma.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.pragma.model.loanstatus.gateways.LoanStatusRepository;
import co.com.pragma.model.loantype.gateways.LoanTypeRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public class LoanUseCase {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final LoanStatusRepository loanStatusRepository;
    private final UserGateway userGateway;
    private final LoggerGateway logger;
    private final TransactionalGateway transactional;

    public LoanUseCase(LoanApplicationRepository loanApplicationRepository, LoanTypeRepository loanTypeRepository, LoanStatusRepository loanStatusRepository, UserGateway userGateway, LoggerGateway logger, TransactionalGateway transactional) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.loanTypeRepository = loanTypeRepository;
        this.loanStatusRepository = loanStatusRepository;
        this.userGateway = userGateway;
        this.logger = logger;
        this.transactional = transactional;
    }

    public Mono<LoanApplication> createLoan(LoanApplication loanApplication) {
        logger.info("Iniciando creación de solicitud de préstamo");

        return userGateway.getUserByDni(loanApplication.getDni())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("El usuario no fue encontrado")))
                .doOnNext(user -> {
                    loanApplication.setEmail(user.getEmail());
                    logger.info("Email del usuario asignado: {}", user.getEmail());
                })
                .then(loanTypeRepository.findLoanTypeById(loanApplication.getLoanTypeId()))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("El tipo de préstamo no existe")))
                .doOnNext(type -> logger.info("Tipo de préstamo encontrado: {}", type.getName()))
                .then(loanStatusRepository.findStatusByName("PENDIENTE"))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No existe estado PENDIENTE")))
                .flatMap(statusId -> {
                    loanApplication.setStatusId(statusId);
                    loanApplication.setApplicationDate(LocalDate.now());
                    logger.info("Preparando solicitud antes de guardar");
                    return loanApplicationRepository.saveLoanApplication(loanApplication);
                })
                .doOnSuccess(saved -> logger.info("Solicitud guardada con éxito con id: {}", saved.getApplicationId()))
                .doOnError(err -> logger.error("Error al guardar la solicitud"))
                .doOnTerminate(() -> logger.info("Proceso de creación de solicitud finalizado"));
    }
}
