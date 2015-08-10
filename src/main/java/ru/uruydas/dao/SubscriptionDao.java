package ru.uruydas.dao;

import ru.uruydas.model.subscriptions.Subscription;
import ru.uruydas.model.users.User;

public interface SubscriptionDao {

    Subscription getByUser(User user);

    void create(User subscriber, User author);
}
