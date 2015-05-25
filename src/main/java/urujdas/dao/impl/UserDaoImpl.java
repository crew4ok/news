package urujdas.dao.impl;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urujdas.dao.UserDao;
import urujdas.model.User;
import urujdas.tables.records.UsersRecord;

import java.sql.Timestamp;

import static urujdas.Tables.USERS;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private DSLContext ctx;

    @Override
    public User getById(Long id) {
        UsersRecord usersRecord = ctx.selectFrom(USERS)
                .where(USERS.ID.equal(id))
                .fetchOne();

        if (usersRecord == null) {
            return null;
        } else {
            return usersRecord.into(User.class);
        }
    }

    @Override
    public User getByUsername(String username) {
        UsersRecord usersRecord = ctx.selectFrom(USERS)
                .where(USERS.USERNAME.equal(username))
                .fetchOne();

        if (usersRecord == null) {
            return null;
        } else {
            return usersRecord.into(User.class);
        }
    }

    @Override
    public User create(User user) {
        return ctx.insertInto(USERS)
                .set(USERS.USERNAME, user.getUsername())
                .set(USERS.PASSWORD, user.getPassword())
                .set(USERS.FIRSTNAME, user.getFirstname())
                .set(USERS.LASTNAME, user.getLastname())
                .set(USERS.BIRTH_DATE, user.getBirthDate() != null ? Timestamp.valueOf(user.getBirthDate()) : null)
                .set(USERS.EMAIL, user.getEmail())
                .set(USERS.GENDER, user.getGender() != null ? user.getGender().name() : null)
                .set(USERS.PHONE, user.getPhone())
                .returning(USERS.fields())
                .fetchOne()
                .into(User.class);
    }


}
