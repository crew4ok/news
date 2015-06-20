package urujdas.model;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import urujdas.model.news.News;
import urujdas.model.users.User;

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
