package urujdas.dao.impl;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urujdas.dao.SubscriptionDao;
import urujdas.model.Subscription;
import urujdas.model.User;
import urujdas.tables.records.SubscriptionsRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static urujdas.tables.SubscriptionsTable.SUBSCRIPTIONS;
import static urujdas.tables.UsersTable.USERS;

@Repository
public class SubscriptionDaoImpl implements SubscriptionDao {

    @Autowired
    private DSLContext ctx;

    @Override
    public Subscription getByUser(User user) {
        List<Long> authorsIds = ctx.selectFrom(SUBSCRIPTIONS)
                .where(SUBSCRIPTIONS.SUBSCRIBER_ID.equal(user.getId()))
                .fetch()
                .map(SubscriptionsRecord::getAuthorId);

        List<User> authors = ctx.select(getUserFields())
                .from(USERS)
                .where(USERS.ID.in(authorsIds))
                .fetch()
                .into(User.class);

        return new Subscription(user, authors);
    }

    @Override
    public void create(User subscriber, User author) {
        ctx.insertInto(SUBSCRIPTIONS)
                .set(SUBSCRIPTIONS.SUBSCRIBER_ID, subscriber.getId())
                .set(SUBSCRIPTIONS.AUTHOR_ID, author.getId())
                .execute();
    }

    private List<Field<?>> getUserFields() {
        List<Field<?>> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(USERS.fields()));
        fields.remove(USERS.PASSWORD);
        return fields;
    }
}
