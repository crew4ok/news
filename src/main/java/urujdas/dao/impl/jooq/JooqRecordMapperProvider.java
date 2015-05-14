package urujdas.dao.impl.jooq;

import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordMapperProvider;
import org.jooq.RecordType;
import urujdas.dao.impl.jooq.mappers.UserRecordMapper;
import urujdas.model.User;

public class JooqRecordMapperProvider implements RecordMapperProvider {

    @Override
    public <R extends Record, E> RecordMapper<R, E> provide(RecordType<R> recordType, Class<? extends E> type) {
        if (User.class.equals(type)) {
            return (RecordMapper<R, E>) new UserRecordMapper();
        }

        throw new RuntimeException("Mapper for type " + type.getName() + " is not found");
    }
}
