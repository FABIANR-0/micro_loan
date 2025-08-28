package co.com.pragma.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static co.com.pragma.api.util.Constants.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoanRequest {

    @NotNull(message = VALID_AMOUNT)
    @DecimalMin(value= "0.1", message = VALID_AMOUNT_MIN)
    BigDecimal amount;

    @NotNull(message = VALID_TERM)
    @Min(value = 1, message = VALID_TERM_MIN)
    Integer term;

    @NotBlank(message = VALID_DOCUMENT)
    @Size(max = 20, message = VALID_DOCUMENT_SIZE)
    String dni;

    @NotNull(message = VALID_LOAN_TYPE)
    @Positive(message = VALID_LOAN_TYPE_NUMBER)
    @JsonProperty("loan_type_id")
    Integer loanTypeId;
}
