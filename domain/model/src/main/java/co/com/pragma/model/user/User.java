package co.com.pragma.model.user;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private Long userId;

    private String name;

    private String dni;

    private String email;

    private BigDecimal baseSalary;
}
