package accountbalance.task.service;


import accountbalance.task.DTO.BalanceResponse;
import accountbalance.task.DTO.TransactionRequest;
import accountbalance.task.model.AccountBalance;
import accountbalance.task.model.Transaction;
import accountbalance.task.repository.AccountBalanceRepository;
import accountbalance.task.repository.TransactionRepository;
import accountbalance.task.util.CurrencyConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountBalanceRepository balanceRepo;
    private final TransactionRepository transactionRepo;
    private final R2dbcEntityTemplate entityTemplate;

    public AccountService(AccountBalanceRepository balanceRepo, TransactionRepository transactionRepo, R2dbcEntityTemplate entityTemplate) {
        this.balanceRepo = balanceRepo;
        this.transactionRepo = transactionRepo;
        this.entityTemplate = entityTemplate;
    }

    public Mono<AccountBalance> createBalance(String name) {
        return balanceRepo.existsByName(name)
                .flatMap(exists -> {
                    if (exists) return Mono.error(new IllegalStateException("Balance name already exists"));

                    AccountBalance balance = AccountBalance.builder()
                            .id(UUID.randomUUID())
                            .name(name)
                            .createdAt(LocalDateTime.now())
                            .build();

                    return entityTemplate.insert(AccountBalance.class).using(balance);
                });
    }

    public Mono<BalanceResponse> getBalance(String name) {
        return balanceRepo.findByName(name)
                .flatMap(balance -> transactionRepo.findAllByBalanceId(balance.getId())
                        .map(t -> CurrencyConverter.toUsd(t.getAmount(), t.getCurrency())
                                .multiply(t.getType().equals("WITHDRAW") ? BigDecimal.valueOf(-1) : BigDecimal.ONE))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .map(total -> new BalanceResponse(balance.getName(), total)));
    }

    public Mono<Transaction> addTransaction(String name, TransactionRequest request) {
        return balanceRepo.findByName(name)
                .switchIfEmpty(Mono.error(new IllegalStateException("Balance not found")))
                .flatMap(balance -> {
                    BigDecimal amountInUsd = CurrencyConverter.toUsd(request.getAmount(), request.getCurrency());
                    return transactionRepo.findAllByBalanceId(balance.getId())
                            .map(t -> CurrencyConverter.toUsd(t.getAmount(), t.getCurrency())
                                    .multiply(t.getType().equals("WITHDRAW") ? BigDecimal.valueOf(-1) : BigDecimal.ONE))
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
                            .flatMap(currentBalance -> {
                                if (request.getType().equals("WITHDRAW") && currentBalance.compareTo(amountInUsd) < 0) {
                                    return Mono.error(new IllegalStateException("Insufficient funds"));
                                }
                                Transaction tx = Transaction.builder()
                                        .id(UUID.randomUUID())
                                        .balanceId(balance.getId())
                                        .type(request.getType())
                                        .amount(request.getAmount())
                                        .currency(request.getCurrency())
                                        .timestamp(LocalDateTime.now())
                                        .build();
                                return entityTemplate.insert(Transaction.class).using(tx);
                            });
                });
    }

    public Flux<Transaction> getTransactions(String name) {
        return balanceRepo.findByName(name)
                .flatMapMany(balance -> transactionRepo.findAllByBalanceId(balance.getId()));
    }

}
