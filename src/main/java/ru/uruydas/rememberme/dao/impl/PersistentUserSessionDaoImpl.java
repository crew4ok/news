package ru.uruydas.rememberme.dao.impl;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.uruydas.rememberme.dao.PersistentUserSessionDao;
import ru.uruydas.rememberme.model.PersistentUserSession;
import ru.uruydas.tables.records.PersistentUserSessionsRecord;

import static ru.uruydas.tables.PersistentUserSessionsTable.PERSISTENT_USER_SESSIONS;

@Repository
public class PersistentUserSessionDaoImpl implements PersistentUserSessionDao {

    @Autowired
    private DSLContext ctx;

    @Override
    public PersistentUserSession findByToken(String token) {
        PersistentUserSessionsRecord record = ctx.selectFrom(PERSISTENT_USER_SESSIONS)
                .where(PERSISTENT_USER_SESSIONS.TOKEN.equal(token))
                .fetchOne();

        if (record != null) {
            return record.into(PersistentUserSession.class);
        }

        return null;
    }

    @Override
    public PersistentUserSession save(PersistentUserSession session) {
        return ctx.insertInto(PERSISTENT_USER_SESSIONS)
                .set(PERSISTENT_USER_SESSIONS.TOKEN, session.getToken())
                .set(PERSISTENT_USER_SESSIONS.USER_ID, session.getUserId())
                .returning(PERSISTENT_USER_SESSIONS.fields())
                .fetchOne()
                .into(PersistentUserSession.class);
    }

    @Override
    public void deleteByToken(String token) {
        ctx.deleteFrom(PERSISTENT_USER_SESSIONS)
                .where(PERSISTENT_USER_SESSIONS.TOKEN.equal(token));
    }
}
