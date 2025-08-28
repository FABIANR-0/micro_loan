package co.com.pragma.model.loanapplication;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanApplication {
    private Long applicationId;
    private BigDecimal amount;
    private Integer term; // en meses
    private String email;
    private String dni;
    private Long statusId;
    private Long loanTypeId;
    private LocalDate applicationDate;
}
