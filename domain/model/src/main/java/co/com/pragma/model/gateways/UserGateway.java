package co.com.pragma.model.gateways;

import co.com.pragma.model.user.User;
import reactor.core.publisher.Mono;

public interface UserGateway {
    Mono<User> getUserByDni(String dni);
}
