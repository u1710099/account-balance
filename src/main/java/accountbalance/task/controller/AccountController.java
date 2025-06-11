package accountbalance.task.controller;

import accountbalance.task.DTO.BalanceResponse;
import accountbalance.task.DTO.TransactionRequest;
import accountbalance.task.model.AccountBalance;
import accountbalance.task.model.Transaction;
import accountbalance.task.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/balances")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{name}")
    public Mono<AccountBalance> createBalance(@PathVariable String name) {
        return accountService.createBalance(name);
    }

    @GetMapping("/{name}")
    public Mono<BalanceResponse> getBalance(@PathVariable String name) {
        return accountService.getBalance(name);
    }

    @PostMapping("/{name}/transactions")
    public Mono<Transaction> addTransaction(@PathVariable String name,
                                            @RequestBody TransactionRequest request) {
        return accountService.addTransaction(name, request);
    }

    @GetMapping("/{name}/transactions")
    public Flux<Transaction> getTransactions(@PathVariable String name) {
        return accountService.getTransactions(name);
    }
}
