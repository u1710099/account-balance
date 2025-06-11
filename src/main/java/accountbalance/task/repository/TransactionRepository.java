package accountbalance.task.repository;

import accountbalance.task.model.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface TransactionRepository extends ReactiveCrudRepository<Transaction, UUID> {

    Flux<Transaction> findAllByBalanceId(UUID balanceId);

}
