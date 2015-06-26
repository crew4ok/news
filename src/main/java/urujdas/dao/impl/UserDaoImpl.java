package urujdas.dao.impl;

import org.jooq.DSLContext;
import org.jooq.Record1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urujdas.dao.UserDao;
import urujdas.dao.exception.NotFoundException;
import urujdas.model.users.User;
import urujdas.tables.records.UsersRecord;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static urujdas.Tables.USERS;
import static urujdas.util.MapperUtils.fromNullable;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private DSLContext ctx;

    @Override
    public boolean checkExists(String username) {
        Record1<Integer> record = ctx.selectOne()
                .from(USERS)
                .where(USERS.USERNAME.equal(username))
                .fetchOne();

        return record != null;
    }

    @Override
    public User getById(Long id) {
        UsersRecord usersRecord = ctx.selectFrom(USERS)
                .where(USERS.ID.equal(id))
                .fetchOne();

        if (usersRecord != null) {
            return usersRecord.into(User.class);
        }

        throw new NotFoundException(User.class, id);
    }

    @Override
    public User getByUsername(String username) {
        UsersRecord usersRecord = ctx.selectFrom(USERS)
                .where(USERS.USERNAME.equal(username))
                .fetchOne();

        if (usersRecord != null) {
            return usersRecord.into(User.class);
        }

        throw new NotFoundException(User.class, username);
    }

    @Override
    public User create(User user) {
        Timestamp birthDateTimestamp = fromNullable(user.getBirthDate(), (LocalDateTime ldt) -> Timestamp.valueOf(ldt));
        String genderName = fromNullable(user.getGender(), Enum::name);
        String genderPreferencesName = fromNullable(user.getGenderPreferences(), Enum::name);
        String relationsPreferences = fromNullable(user.getRelationsPreferences(), Enum::name);

        return ctx.insertInto(USERS)
                .set(USERS.USERNAME, user.getUsername())
                .set(USERS.PASSWORD, user.getPassword())
                .set(USERS.FIRSTNAME, user.getFirstname())
                .set(USERS.LASTNAME, user.getLastname())
                .set(USERS.BIRTH_DATE, birthDateTimestamp)
                .set(USERS.EMAIL, user.getEmail())
                .set(USERS.GENDER, genderName)
                .set(USERS.PHONE, user.getPhone())
                .set(USERS.GENDER_PREFERENCES, genderPreferencesName)
                .set(USERS.RELATIONS_PREFERENCES, relationsPreferences)
                .returning(USERS.fields())
                .fetchOne()
                .into(User.class);
    }


}
