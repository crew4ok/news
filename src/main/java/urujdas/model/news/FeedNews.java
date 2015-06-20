package urujdas.model.news;

import net.karneim.pojobuilder.GeneratePojoBuilder;

public class FeedNews {
    private final Long id;
    private final String title;
    private final String body;
    private final String username;
    private final String firstname;
    private final String lastname;
    private final int commentsCount;
    private final int likesCount;
    private final boolean currentUserLiked;
    private final boolean currentUserFavoured;

    @GeneratePojoBuilder
    public FeedNews(Long id, String title, String body, String username, String firstname, String lastname,
                    int commentsCount, int likesCount, boolean currentUserLiked, boolean currentUserFavoured) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.commentsCount = commentsCount;
        this.likesCount = likesCount;
        this.currentUserLiked = currentUserLiked;
        this.currentUserFavoured = currentUserFavoured;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public String getBody() {
        return body;
    }

    public boolean isCurrentUserLiked() {
        return currentUserLiked;
    }

    public boolean isCurrentUserFavoured() {
        return currentUserFavoured;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedNews feedNews = (FeedNews) o;

        if (commentsCount != feedNews.commentsCount) return false;
        if (likesCount != feedNews.likesCount) return false;
        if (currentUserLiked != feedNews.currentUserLiked) return false;
        if (currentUserFavoured != feedNews.currentUserFavoured) return false;
        if (id != null ? !id.equals(feedNews.id) : feedNews.id != null) return false;
        if (title != null ? !title.equals(feedNews.title) : feedNews.title != null) return false;
        if (body != null ? !body.equals(feedNews.body) : feedNews.body != null) return false;
        if (username != null ? !username.equals(feedNews.username) : feedNews.username != null) return false;
        if (firstname != null ? !firstname.equals(feedNews.firstname) : feedNews.firstname != null) return false;
        return !(lastname != null ? !lastname.equals(feedNews.lastname) : feedNews.lastname != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + commentsCount;
        result = 31 * result + likesCount;
        result = 31 * result + (currentUserLiked ? 1 : 0);
        result = 31 * result + (currentUserFavoured ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FeedNews{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", commentsCount=" + commentsCount +
                ", likesCount=" + likesCount +
                ", currentUserLiked=" + currentUserLiked +
                ", currentUserFavoured=" + currentUserFavoured +
                '}';
    }

    public static FeedNewsBuilder builder() {
        return new FeedNewsBuilder();
    }

}
