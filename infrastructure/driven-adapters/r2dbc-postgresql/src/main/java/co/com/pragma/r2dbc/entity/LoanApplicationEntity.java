package co.com.pragma.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("loan_application")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationEntity {

    @Id
    @Column("application_id")
    private Long applicationId;

    @Column("amount")
    private BigDecimal amount;

    @Column("term")
    private Integer term;

    @Column("email")
    private String email;

    @Column("status_id")
    private Long statusId;

    @Column("loan_type_id")
    private Long loanTypeId;

    @Column("application_date")
    private LocalDateTime applicationDate;
}
