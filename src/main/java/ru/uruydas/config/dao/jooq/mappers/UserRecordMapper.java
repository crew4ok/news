package ru.uruydas.config.dao.jooq.mappers;

import org.jooq.Record;
import org.jooq.RecordMapper;
import ru.uruydas.users.model.Gender;
import ru.uruydas.users.model.GenderPreferences;
import ru.uruydas.users.model.RelationsPreferences;
import ru.uruydas.users.model.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static ru.uruydas.common.util.MapperUtils.fromNullable;
import static ru.uruydas.tables.UsersTable.USERS;

public class UserRecordMapper implements RecordMapper<Record, User> {

    @Override
    public User map(Record record) {
        LocalDateTime birthDate = fromNullable(record.getValue(USERS.BIRTH_DATE), Timestamp::toLocalDateTime);
        Gender gender = fromNullable(record.getValue(USERS.GENDER), Gender::valueOf);
        LocalDateTime pullUpDate = fromNullable(record.getValue(USERS.PULL_UP_DATE), Timestamp::toLocalDateTime);

        GenderPreferences genderPreferences = fromNullable(
                record.getValue(USERS.GENDER_PREFERENCES),
                GenderPreferences::valueOf
        );

        RelationsPreferences relationsPreferences = fromNullable(
                record.getValue(USERS.RELATIONS_PREFERENCES),
                RelationsPreferences::valueOf
        );

        return User.builder()
                .withId(record.getValue(USERS.ID))
                .withUsername(record.getValue(USERS.USERNAME))
                .withFirstname(record.getValue(USERS.FIRSTNAME))
                .withLastname(record.getValue(USERS.LASTNAME))
                .withBirthDate(birthDate)
                .withEmail(record.getValue(USERS.EMAIL))
                .withGender(gender)
                .withPhone(record.getValue(USERS.PHONE))
                .withPullUpDate(pullUpDate)
                .withGenderPreferences(genderPreferences)
                .withRelationsPreferences(relationsPreferences)
                .withQuickBloxId(record.getValue(USERS.QUICK_BLOX_ID))
                .build();
    }
}
