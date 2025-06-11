package accountbalance.task.repository;

import accountbalance.task.model.AccountBalance;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AccountBalanceRepository extends ReactiveCrudRepository<AccountBalance, UUID> {

    Mono<AccountBalance> findByName(String name);
    Mono<Boolean> existsByName(String name);
}
