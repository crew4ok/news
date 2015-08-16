package ru.uruydas.config.dao.jooq.mappers;

import org.jooq.Record;
import org.jooq.RecordMapper;
import ru.uruydas.rememberme.model.PersistentUserSession;

import static ru.uruydas.tables.PersistentUserSessionsTable.PERSISTENT_USER_SESSIONS;

public class PersistentUserSessionRecordMapper implements RecordMapper<Record, PersistentUserSession> {

    @Override
    public PersistentUserSession map(Record record) {
        return new PersistentUserSession(
                record.getValue(PERSISTENT_USER_SESSIONS.ID),
                record.getValue(PERSISTENT_USER_SESSIONS.TOKEN),
                record.getValue(PERSISTENT_USER_SESSIONS.USER_ID)
        );
    }
}
