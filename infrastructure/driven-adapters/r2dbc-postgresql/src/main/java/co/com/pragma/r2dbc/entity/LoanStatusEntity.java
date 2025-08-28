package co.com.pragma.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("loan_status")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanStatusEntity {

    @Id
    @Column("status_id")
    private Long statusId;

    @Column("name")
    private String name;

    @Column("description")
    private String description;
}
