package accountbalance.task.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@Configuration
@EnableR2dbcAuditing
public class R2dbcConfig {
    @Bean
    public ConnectionFactory connectionFactory() {
        // Build ConnectionFactoryOptions directly to ensure the correct driver and options are set.
        // This is often more resilient than relying solely on the R2DBC URL parsing
        // when multiple drivers/configurations might be detected.
        return ConnectionFactories.get(
                ConnectionFactoryOptions.builder()
                        .option(ConnectionFactoryOptions.DRIVER, "postgresql") // Specify the R2DBC driver explicitly
                        .option(ConnectionFactoryOptions.HOST, "localhost")
                        .option(ConnectionFactoryOptions.PORT, 5432)
                        .option(ConnectionFactoryOptions.DATABASE, "account_balance_db")
                        .option(ConnectionFactoryOptions.USER, "postgres")
                        // IMPORTANT: Replace with your actual PostgreSQL password
                        .option(ConnectionFactoryOptions.PASSWORD, "admin")
                        .build()
        );
    }

}
