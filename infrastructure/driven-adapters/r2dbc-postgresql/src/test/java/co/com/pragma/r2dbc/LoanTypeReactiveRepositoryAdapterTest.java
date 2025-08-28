package co.com.pragma.r2dbc;

import co.com.pragma.model.loantype.LoanType;
import co.com.pragma.r2dbc.entity.LoanTypeEntity;
import co.com.pragma.r2dbc.loantype.LoanTypeReactiveRepository;
import co.com.pragma.r2dbc.loantype.LoanTypeReactiveRepositoryAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanTypeReactiveRepositoryAdapterTest {

    @InjectMocks
    LoanTypeReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    LoanTypeReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    private final LoanTypeEntity entity = LoanTypeEntity.builder()
            .loanTypeId(1L)
            .name("Préstamo Personal")
            .minAmount(new BigDecimal("500000"))
            .maxAmount(new BigDecimal("1000000"))
            .interestRate(new BigDecimal("0.120"))
            .autoValidation(true)
            .build();

    private final LoanType model = LoanType.builder()
            .loanTypeId(1L)
            .name("Préstamo Personal")
            .minAmount(new BigDecimal("500000"))
            .maxAmount(new BigDecimal("1000000"))
            .interestRate(new BigDecimal("0.120"))
            .autoValidation(true)
            .build();

    @Test
    void mustFindValueById() {
        when(repository.findById(1L)).thenReturn(Mono.just(entity));
        when(mapper.map(entity, LoanType.class)).thenReturn(model);

        Mono<LoanType> result = repositoryAdapter.findById(1L);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getLoanTypeId().equals(1L)
                        && value.getName().equals("Préstamo Personal"))
                .verifyComplete();
    }

    @Test
    void mustFindAllValues() {
        when(repository.findAll()).thenReturn(Flux.just(entity));
        when(mapper.map(entity, LoanType.class)).thenReturn(model);

        Flux<LoanType> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getName().equals("Préstamo Personal"))
                .verifyComplete();
    }

    @Test
    void mustFindByExample() {
        when(repository.findAll(any(Example.class))).thenReturn(Flux.just(entity));
        when(mapper.map(entity, LoanType.class)).thenReturn(model);

        Flux<LoanType> result = repositoryAdapter.findByExample(model);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getLoanTypeId().equals(1L))
                .verifyComplete();
    }

    @Test
    void mustSaveValue() {
        when(mapper.map(model, LoanTypeEntity.class)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.map(entity, LoanType.class)).thenReturn(model);

        Mono<LoanType> result = repositoryAdapter.save(model);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getLoanTypeId().equals(1L)
                        && value.getName().equals("Préstamo Personal"))
                .verifyComplete();
    }
}