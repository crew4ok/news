package urujdas.model.news;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import urujdas.model.users.User;

import java.time.LocalDateTime;
import java.util.List;

public class News {
    private final Long id;
    private final String title;
    private final String body;
    private final LocalDateTime creationDate;
    private final String location;
    private final User author;
    private final NewsCategory category;
    private final List<Long> imageIds;

    private final int likesCount;
    private final int commentsCount;
    private final boolean currentUserLiked;
    private final boolean currentUserFavoured;

    @GeneratePojoBuilder
    public News(Long id, String title, String body, LocalDateTime creationDate, String location,
                User author, NewsCategory category, List<Long> imageIds,
                int likesCount, int commentsCount,
                boolean currentUserLiked, boolean currentUserFavoured) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.creationDate = creationDate;
        this.location = location;
        this.likesCount = likesCount;
        this.author = author;
        this.category = category;
        this.imageIds = imageIds;
        this.commentsCount = commentsCount;
        this.currentUserLiked = currentUserLiked;
        this.currentUserFavoured = currentUserFavoured;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getLocation() {
        return location;
    }

    public User getAuthor() {
        return author;
    }

    public NewsCategory getCategory() {
        return category;
    }

    public List<Long> getImageIds() {
        return imageIds;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public boolean isCurrentUserLiked() {
        return currentUserLiked;
    }

    public boolean isCurrentUserFavoured() {
        return currentUserFavoured;
    }

    public static NewsBuilder fromNews(News news) {
        return new NewsBuilder()
                .withId(news.id)
                .withTitle(news.title)
                .withBody(news.body)
                .withCreationDate(news.creationDate)
                .withLocation(news.location)
                .withLikesCount(news.likesCount)
                .withAuthor(news.author)
                .withCategory(news.category)
                .withImageIds(news.imageIds)
                .withLikesCount(news.likesCount)
                .withCommentsCount(news.commentsCount)
                .withCurrentUserLiked(news.currentUserLiked)
                .withCurrentUserFavoured(news.currentUserFavoured);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News news = (News) o;

        if (likesCount != news.likesCount) return false;
        if (commentsCount != news.commentsCount) return false;
        if (currentUserLiked != news.currentUserLiked) return false;
        if (currentUserFavoured != news.currentUserFavoured) return false;
        if (id != null ? !id.equals(news.id) : news.id != null) return false;
        if (title != null ? !title.equals(news.title) : news.title != null) return false;
        if (body != null ? !body.equals(news.body) : news.body != null) return false;
        if (creationDate != null ? !creationDate.equals(news.creationDate) : news.creationDate != null) return false;
        if (location != null ? !location.equals(news.location) : news.location != null) return false;
        if (author != null ? !author.equals(news.author) : news.author != null) return false;
        if (category != null ? !category.equals(news.category) : news.category != null) return false;
        return !(imageIds != null ? !imageIds.equals(news.imageIds) : news.imageIds != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (imageIds != null ? imageIds.hashCode() : 0);
        result = 31 * result + likesCount;
        result = 31 * result + commentsCount;
        result = 31 * result + (currentUserLiked ? 1 : 0);
        result = 31 * result + (currentUserFavoured ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", creationDate=" + creationDate +
                ", location='" + location + '\'' +
                ", author=" + author +
                ", category=" + category +
                ", imageIds=" + imageIds +
                ", likesCount=" + likesCount +
                ", commentsCount=" + commentsCount +
                ", currentUserLiked=" + currentUserLiked +
                ", currentUserFavoured=" + currentUserFavoured +
                '}';
    }

    public static NewsBuilder builder() {
        return new NewsBuilder();
    }
}
