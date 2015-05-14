package urujdas.dao.impl.jooq.mappers;

import org.jooq.RecordMapper;
import urujdas.model.Gender;
import urujdas.model.User;
import urujdas.tables.records.UsersRecord;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserRecordMapper implements RecordMapper<UsersRecord, User> {

    @Override
    public User map(UsersRecord record) {
        Timestamp birthdateTimestamp = record.getBirthdate();
        LocalDateTime birthday = null;
        if (birthdateTimestamp != null) {
            birthday = birthdateTimestamp.toLocalDateTime();
        }

        String genderStr = record.getGender();
        Gender gender = null;
        if (genderStr != null) {
            gender = Gender.valueOf(genderStr);
        }

        return User.builder()
                .withId(record.getId())
                .withUsername(record.getUsername())
                .withPassword(record.getPassword())
                .withFirstname(record.getFirstname())
                .withLastname(record.getLastname())
                .withBirthDate(birthday)
                .withEmail(record.getEmail())
                .withGender(gender)
                .withPhone(record.getPhonenumber())
                .build();
    }
}
