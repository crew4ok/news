package ru.uruydas.comments.model;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import ru.uruydas.users.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class Comment {
    private final Long id;
    private final String body;
    private final LocalDateTime creationDate;
    private final Long newsId;
    private final User author;
    private final List<Long> imageIds;

    @GeneratePojoBuilder
    public Comment(Long id, String body, LocalDateTime creationDate, Long newsId, User author, List<Long> imageIds) {
        this.id = id;
        this.body = body;
        this.creationDate = creationDate;
        this.newsId = newsId;
        this.author = author;
        this.imageIds = imageIds;
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Long getNewsId() {
        return newsId;
    }

    public User getAuthor() {
        return author;
    }

    public List<Long> getImageIds() {
        return imageIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (id != null ? !id.equals(comment.id) : comment.id != null) return false;
        if (body != null ? !body.equals(comment.body) : comment.body != null) return false;
        if (creationDate != null ? !creationDate.equals(comment.creationDate) : comment.creationDate != null)
            return false;
        if (newsId != null ? !newsId.equals(comment.newsId) : comment.newsId != null) return false;
        if (author != null ? !author.equals(comment.author) : comment.author != null) return false;
        return !(imageIds != null ? !imageIds.equals(comment.imageIds) : comment.imageIds != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (newsId != null ? newsId.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (imageIds != null ? imageIds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", creationDate=" + creationDate +
                ", newsId=" + newsId +
                ", author=" + author +
                ", imageIds=" + imageIds +
                '}';
    }

    public static CommentBuilder builder() {
        return new CommentBuilder();
    }

    public static CommentBuilder fromComment(Comment comment) {
        return new CommentBuilder()
                .withId(comment.id)
                .withBody(comment.body)
                .withCreationDate(comment.creationDate)
                .withNewsId(comment.newsId)
                .withAuthor(comment.author)
                .withImageIds(comment.imageIds);
    }
}
