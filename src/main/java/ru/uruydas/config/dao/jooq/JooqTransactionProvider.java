package ru.uruydas.config.dao.jooq;

import org.jooq.Transaction;
import org.jooq.TransactionContext;
import org.jooq.TransactionProvider;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class JooqTransactionProvider implements TransactionProvider {

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Override
    public void begin(TransactionContext ctx) throws DataAccessException {
        TransactionStatus transaction = transactionManager.getTransaction(
                new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_NESTED)
        );
        ctx.transaction(new SpringTransaction(transaction));
    }

    @Override
    public void commit(TransactionContext ctx) throws DataAccessException {
        SpringTransaction transaction = (SpringTransaction)ctx.transaction();
        transactionManager.commit(transaction.tx);
    }

    @Override
    public void rollback(TransactionContext ctx) throws DataAccessException {
        SpringTransaction transaction = (SpringTransaction)ctx.transaction();
        transactionManager.rollback(transaction.tx);
    }

    private static class SpringTransaction implements Transaction {
        final TransactionStatus tx;

        public SpringTransaction(TransactionStatus tx) {
            this.tx = tx;
        }
    }
}
