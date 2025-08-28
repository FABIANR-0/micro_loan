package co.com.pragma.model.loanstatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanStatus {
    private Long statusId;
    private String name;
    private String description;
}
