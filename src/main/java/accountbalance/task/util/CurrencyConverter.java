package accountbalance.task.util;

import java.math.BigDecimal;
import java.util.Map;

public class CurrencyConverter {

    private static final Map<String, BigDecimal> RATES = Map.of(
            "USD", BigDecimal.valueOf(1.0),
            "EUR", BigDecimal.valueOf(1.1),
            "BYN", BigDecimal.valueOf(0.3),
            "RUB", BigDecimal.valueOf(0.01)
    );

    public static BigDecimal toUsd(BigDecimal amount, String currency) {
        BigDecimal rate = RATES.get(currency);
        if (rate == null) throw new IllegalArgumentException("Unsupported currency: " + currency);
        return amount.multiply(rate);
    }
}
