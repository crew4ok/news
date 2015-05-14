package urujdas.dao.impl;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urujdas.dao.UserDao;
import urujdas.model.User;
import urujdas.tables.records.UsersRecord;

import java.sql.Timestamp;
import java.util.Optional;

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
    public void create(User user) {
        ctx.insertInto(USERS)
                .set(USERS.USERNAME, user.getUsername())
                .set(USERS.PASSWORD, user.getPassword())
                .set(USERS.FIRSTNAME, user.getFirstname().orElse(null))
                .set(USERS.LASTNAME, user.getLastname().orElse(null))
                .set(USERS.BIRTHDATE, user.getBirthDate().map(Timestamp::valueOf).orElse(null))
                .set(USERS.EMAIL, user.getEmail().orElse(null))
                .set(USERS.GENDER, user.getGender().map(Enum::name).orElse(null))
                .set(USERS.PHONENUMBER, user.getPhone().orElse(null))
                .execute();
    }


}
