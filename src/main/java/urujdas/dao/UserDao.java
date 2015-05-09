package urujdas.dao;

import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urujdas.model.Gender;
import urujdas.model.User;
import urujdas.tables.records.UserRecord;

import java.sql.Timestamp;

import static urujdas.Tables.USER;

@Repository
public class UserDao {
    private static class UserRecordMapper implements RecordMapper<UserRecord, User> {

        @Override
        public User map(UserRecord record) {
            return User.builder()
                    .withId(record.getId())
                    .withUsername(record.getUsername())
                    .withPassword(record.getPassword())
                    .withFirstname(record.getFirstname())
                    .withLastname(record.getLastname())
                    .withBirthDate(record.getBirthdate().toLocalDateTime())
                    .withEmail(record.getEmail())
                    .withGender(Gender.valueOf(record.getGender()))
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
