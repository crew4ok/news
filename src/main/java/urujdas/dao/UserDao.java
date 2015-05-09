package urujdas.dao;

import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urujdas.model.Gender;
import urujdas.model.User;
import urujdas.tables.records.UserRecord;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static urujdas.Tables.USER;

@Repository
public class UserDao {
    private static class UserRecordMapper implements RecordMapper<UserRecord, User> {

        @Override
        public User map(UserRecord record) {
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


    @Autowired
    private DSLContext ctx;

    public User getById(Long id) {
        return ctx.selectFrom(USER)
                .where(USER.ID.equal(id))
                .fetchOne(new UserRecordMapper());
    }

    public Long create(User user) {
        return ctx.insertInto(USER)
                .set(USER.USERNAME, user.getUsername())
                .set(USER.PASSWORD, user.getPassword())
                .set(USER.FIRSTNAME, user.getFirstname())
                .set(USER.LASTNAME, user.getLastname())
                .set(USER.BIRTHDATE, user.getBirthDate() != null ? Timestamp.valueOf(user.getBirthDate()) : null)
                .set(USER.EMAIL, user.getEmail())
                .set(USER.GENDER, user.getGender().name())
                .set(USER.PHONENUMBER, user.getPhone())
                .returning(USER.ID)
                .fetchOne()
                .getId();
    }
}
