package urujdas.model;

import java.util.List;

public class Subscription {
    private final User subscriber;
    private final List<User> authors;

    public Subscription(User subscriber, List<User> authors) {
        this.subscriber = subscriber;
        this.authors = authors;
    }

    public User getSubscriber() {
        return subscriber;
    }

    public List<User> getAuthors() {
        return authors;
    }
}
