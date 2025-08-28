package co.com.pragma.api;

import co.com.pragma.api.dto.LoanRequest;
import co.com.pragma.api.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class Handler {

    //private final LoanMapper loanMapper;

   // private final LoanUseCase userUseCase;

    private final ValidationUtils validationUtils;

    public Mono<ServerResponse> createLoan(ServerRequest serverRequest) {
        return validationUtils.validateBody(serverRequest, LoanRequest.class)
                .then(ServerResponse.status(HttpStatus.CREATED)
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("message","Loan created successfully")));
    }
}
