package ru.uruydas.subscriptions.dao;

import ru.uruydas.subscriptions.model.Subscription;
import ru.uruydas.users.model.User;

public interface SubscriptionDao {

    Subscription getByUser(User user);

    void create(User subscriber, User author);
}
