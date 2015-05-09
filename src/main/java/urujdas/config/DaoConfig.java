package urujdas.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.Transaction;
import org.jooq.TransactionContext;
import org.jooq.TransactionProvider;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

@Configuration
@ComponentScan("urujdas.dao")
public class  DaoConfig {

    @Bean
    public DataSource dataSource() {
        try {
            Class.forName("org.postgresql.ds.PGSimpleDataSource");
        } catch (Exception e) {
            throw new RuntimeException("Cannot load postgres data source", e);
        }
        String dbHostname = System.getenv("DB_HOSTNAME");
        String dbDatabase = System.getenv("DB_DATABASE");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");

        String jdbcUrl = "jdbc:postgresql://" + dbHostname + "/" + dbDatabase;

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(dbUser);
        config.setPassword(dbPassword);

        return new HikariDataSource(config);
    }

    @Bean
    public TransactionAwareDataSourceProxy transactionAwareDataSource() {
        return new TransactionAwareDataSourceProxy(dataSource());
    }

    @Bean
    public ConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(transactionAwareDataSource());
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DSLContext dslContext() {
        return new DefaultDSLContext(jooqConfiguration());
    }

    @Bean
    public org.jooq.Configuration jooqConfiguration() {
        return new DefaultConfiguration()
                .set(SQLDialect.POSTGRES_9_3)
                .set(connectionProvider())
                .set(transactionProvider())
                .derive();
    }

    @Bean
    public TransactionProvider transactionProvider() {
        return new TransactionProvider() {
            private final Logger LOGGER = LoggerFactory.getLogger(TransactionProvider.class);

            @Autowired
            private DataSourceTransactionManager transactionManager;

            @Override
            public void begin(TransactionContext ctx) throws DataAccessException {
                LOGGER.info("starting transaction");

                TransactionStatus transaction = transactionManager.getTransaction(
                        new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_NESTED));

                ctx.transaction(new SpringTransaction(transaction));
            }

            @Override
            public void commit(TransactionContext ctx) throws DataAccessException {
                LOGGER.info("committing transaction");

                transactionManager.commit(((SpringTransaction)ctx.transaction()).tx);
            }

            @Override
            public void rollback(TransactionContext ctx) throws DataAccessException {
                LOGGER.info("rollbacking transaction");

                transactionManager.rollback(((SpringTransaction) ctx.transaction()).tx);
            }
        };
    }

    private static class SpringTransaction implements Transaction {
        final TransactionStatus tx;

        public SpringTransaction(TransactionStatus tx) {
            this.tx = tx;
        }
    }
}
