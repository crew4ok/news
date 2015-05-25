package urujdas.dao.impl.jooq.mappers;

import org.jooq.Record;
import org.jooq.RecordMapper;
import urujdas.model.Gender;
import urujdas.model.User;
import urujdas.tables.UsersTable;
import urujdas.tables.records.UsersRecord;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static urujdas.tables.UsersTable.USERS;

public class UserRecordMapper implements RecordMapper<Record, User> {

    @Override
    public User map(Record record) {
        LocalDateTime birthDate = Optional.ofNullable(record.getValue(USERS.BIRTH_DATE))
                .map(Timestamp::toLocalDateTime)
                .orElse(null);

        Gender gender = Optional.ofNullable(record.getValue(USERS.GENDER))
                .map(Gender::valueOf)
                .orElse(null);

        return User.builder()
                .withId(record.getValue(USERS.ID))
                .withUsername(record.getValue(USERS.USERNAME))
                .withPassword(record.getValue(USERS.PASSWORD))
                .withFirstname(record.getValue(USERS.FIRSTNAME))
                .withLastname(record.getValue(USERS.LASTNAME))
                .withBirthDate(birthDate)
                .withEmail(record.getValue(USERS.EMAIL))
                .withGender(gender)
                .withPhone(record.getValue(USERS.PHONE))
                .build();
    }
}
