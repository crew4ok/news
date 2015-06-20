package urujdas.dao;

import urujdas.model.subscriptions.Subscription;
import urujdas.model.users.User;

public interface SubscriptionDao {

    Subscription getByUser(User user);

    void create(User subscriber, User author);
}
