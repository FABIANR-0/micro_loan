package co.com.pragma.consumer;

import co.com.pragma.model.gateways.UserGateway;
import co.com.pragma.model.user.User;
import co.com.pragma.consumer.exception.ResourceNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RestConsumer implements UserGateway {

    private final WebClient client;

    @Override
    @CircuitBreaker(name = "getUserByDni" /*, fallbackMethod = "testGetOk"*/)
    public Mono<User> getUserByDni(String dni) {
        return client
                .get()
                .uri("/api/v1/user/dni/{dni}", new Object[]{dni})
                .retrieve()
                .onStatus(
                status -> status.value() == 404,
                response -> Mono.error(new ResourceNotFoundException("Usuario no encontrado con documento: " + dni))
                ).bodyToMono(User.class)
                .onErrorResume(WebClientRequestException.class, ex -> Mono.error(new ResourceNotFoundException("No se pudo obtener el usuario, servicio no disponible")));
    }
}
