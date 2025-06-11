package accountbalance.task.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BalanceResponse {

    private String name;
    private BigDecimal balanceInUsd;

}
