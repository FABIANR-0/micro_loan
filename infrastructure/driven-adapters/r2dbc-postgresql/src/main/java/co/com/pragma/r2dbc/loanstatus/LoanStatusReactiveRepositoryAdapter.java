package co.com.pragma.r2dbc.loanstatus;

import co.com.pragma.model.loanstatus.LoanStatus;
import co.com.pragma.model.loanstatus.gateways.LoanStatusRepository;
import co.com.pragma.r2dbc.entity.LoanStatusEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LoanStatusReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        LoanStatus,
        LoanStatusEntity,
        Long,
        LoanStatusReactiveRepository
        > implements LoanStatusRepository {
    public LoanStatusReactiveRepositoryAdapter(LoanStatusReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, LoanStatus.class));
    }

}
