package urujdas.social.dao.impl;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urujdas.model.social.SocialNetworkType;
import urujdas.model.users.User;
import urujdas.social.dao.SocialNetworkUserDao;
import urujdas.tables.records.UsersRecord;

import static urujdas.tables.UsersTable.USERS;

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
