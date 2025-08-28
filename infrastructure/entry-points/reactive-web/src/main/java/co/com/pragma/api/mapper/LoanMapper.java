package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.LoanRequest;
import co.com.pragma.model.loanapplication.LoanApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    LoanApplication toLoanApplication(LoanRequest dto);

    LoanRequest toDto(LoanApplication loanApplication);
}
