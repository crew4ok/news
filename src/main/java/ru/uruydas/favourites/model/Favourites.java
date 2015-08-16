package ru.uruydas.favourites.model;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import ru.uruydas.news.model.News;
import ru.uruydas.users.model.User;

import java.util.List;

public class Favourites {
    private final User user;
    private final List<News> favourites;

    @GeneratePojoBuilder
    public Favourites(User user, List<News> favourites) {
        this.user = user;
        this.favourites = favourites;
    }

    public User getUser() {
        return user;
    }

    public List<News> getFavourites() {
        return favourites;
    }

    public static FavouritesBuilder builder() {
        return new FavouritesBuilder();
    }
}
