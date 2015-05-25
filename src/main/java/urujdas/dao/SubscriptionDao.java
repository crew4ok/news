package urujdas.dao;

import urujdas.model.Subscription;
import urujdas.model.User;

public interface SubscriptionDao {

    Subscription getByUser(User user);

    void create(User subscriber, User author);
}
