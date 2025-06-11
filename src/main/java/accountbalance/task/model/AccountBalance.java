package accountbalance.task.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("account_balance")
@Builder
@Data
public class AccountBalance {

    @Id
    private UUID id;
    private String name;
    private LocalDateTime createdAt = LocalDateTime.now();

}
