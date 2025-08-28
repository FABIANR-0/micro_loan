package co.com.pragma.api;

import co.com.pragma.api.dto.LoanRequest;
import co.com.pragma.api.mapper.LoanMapper;
import co.com.pragma.api.util.ValidationUtils;
import co.com.pragma.usecase.loan.LoanUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final LoanMapper loanMapper;

    private final LoanUseCase loanUseCase;

    private final ValidationUtils validationUtils;

    public Mono<ServerResponse> createLoan(ServerRequest serverRequest) {
        return validationUtils.validateBody(serverRequest, LoanRequest.class)
                .flatMap(loanRequest -> loanUseCase.createLoan(loanMapper.toLoanApplication(loanRequest))
                        .flatMap(loan -> ServerResponse.status(HttpStatus.CREATED).bodyValue(loanMapper.toDto(loan)))
                );
    }
}
