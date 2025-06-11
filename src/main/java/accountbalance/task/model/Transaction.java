package accountbalance.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("transaction")
public class Transaction {
    @Id
    private UUID id;
    @Column("balance_id")
    private UUID balanceId;
    private String type; // DEPOSIT or WITHDRAW
    private BigDecimal amount;
    private String currency;
    private LocalDateTime timestamp;
}
