package accountbalance.task.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private String type; // DEPOSIT or WITHDRAW
    private BigDecimal amount;
    private String currency;
}
