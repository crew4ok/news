package ru.uruydas.social.dao.impl;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.uruydas.social.dao.SocialNetworkUserDao;
import ru.uruydas.social.model.SocialNetworkType;
import ru.uruydas.tables.records.UsersRecord;
import ru.uruydas.users.model.User;

import static ru.uruydas.tables.UsersTable.USERS;

@Repository
public class SocialNetworkUserDaoImpl implements SocialNetworkUserDao {

    @Autowired
    private DSLContext ctx;

    @Override
    public User findByNetworkId(SocialNetworkType socialNetworkType, Long socialNetworkUserId) {
        UsersRecord userRecord = ctx.selectFrom(USERS)
                .where(getSocialNetworkCondition(socialNetworkType, socialNetworkUserId))
                .fetchOne();

        if (userRecord != null) {
            return userRecord.into(User.class);
        }

        return null;
    }

    // TODO: refactor into strategies
    private Condition getSocialNetworkCondition(SocialNetworkType socialNetworkType, Long socialNetworkUserId) {
        switch (socialNetworkType) {
            case VK:
                return USERS.VK_ID.equal(socialNetworkUserId);

            default:
                throw new RuntimeException("Not exhaustive switch");
        }
    }
}
