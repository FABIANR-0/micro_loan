package co.com.pragma.model.loanstatus.gateways;

import reactor.core.publisher.Mono;

public interface LoanStatusRepository {
    Mono<Long> findStatusByName(String name);
}
