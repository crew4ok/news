package urujdas.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.RecordMapperProvider;
import org.jooq.SQLDialect;
import org.jooq.TransactionProvider;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import urujdas.dao.impl.jooq.JooqRecordMapperProvider;
import urujdas.dao.impl.jooq.JooqTransactionProvider;
import urujdas.dao.impl.jooq.mappers.CommentRecordMapper;
import urujdas.dao.impl.jooq.mappers.ImageRecordMapper;
import urujdas.dao.impl.jooq.mappers.NewsCategoryRecordMapper;
import urujdas.dao.impl.jooq.mappers.NewsRecordMapper;
import urujdas.dao.impl.jooq.mappers.UserFilterRecordMapper;
import urujdas.dao.impl.jooq.mappers.UserRecordMapper;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "urujdas.dao")
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

        if (dbHostname == null
                || dbDatabase == null
                || dbUser == null
                || dbPassword == null) {
            throw new RuntimeException("Environment variables are incorrect");
        }

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
                .set(jooqRecordMapperProvider())
                .derive();
    }

    @Bean
    public RecordMapperProvider jooqRecordMapperProvider() {
        return new JooqRecordMapperProvider(
                userRecordMapper(),
                userFilterRecordMapper(),
                newsRecordMapper(),
                newsCategoryRecordMapper(),
                commentRecordMapper(),
                imageRecordMapper()
        );
    }

    @Bean
    public UserRecordMapper userRecordMapper() {
        return new UserRecordMapper();
    }

    @Bean
    public UserFilterRecordMapper userFilterRecordMapper() {
        return new UserFilterRecordMapper();
    }

    @Bean
    public NewsRecordMapper newsRecordMapper() {
        return new NewsRecordMapper();
    }

    @Bean
    public NewsCategoryRecordMapper newsCategoryRecordMapper() {
        return new NewsCategoryRecordMapper();
    }

    @Bean
    public CommentRecordMapper commentRecordMapper() {
        return new CommentRecordMapper();
    }

    @Bean
    public ImageRecordMapper imageRecordMapper() {
        return new ImageRecordMapper();
    }

    @Bean
    public TransactionProvider transactionProvider() {
        return new JooqTransactionProvider();
    }
}
