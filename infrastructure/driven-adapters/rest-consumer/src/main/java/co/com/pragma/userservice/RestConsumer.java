package co.com.pragma.userservice;

import co.com.pragma.model.gateways.UserGateway;
import co.com.pragma.model.user.User;
import co.com.pragma.userservice.dto.UserResponse;
import co.com.pragma.userservice.exception.ResourceNotFoundException;
import co.com.pragma.userservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RestConsumer implements UserGateway {

    private final WebClient.Builder webClientBuilder;

    private final UserMapper userMapper;

    @Override
    public Mono<User> getUserByDni(String dni) {
        return webClientBuilder.baseUrl("http://localhost:8080")
                .build()
                .get()
                .uri("/api/v1/user/dni/{dni}", new Object[]{dni})
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        response -> Mono.error(new ResourceNotFoundException("Usuario no encontrado con documento: " + dni))
                )
                .bodyToMono(UserResponse.class)
                .map(userMapper::toUser);
    }

}
