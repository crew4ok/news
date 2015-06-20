package urujdas.model;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import urujdas.model.users.User;
import urujdas.util.Validation;

import java.time.LocalDateTime;

public class Comment {
    private final Long id;
    private final String body;
    private final LocalDateTime creationDate;
    private final Long newsId;
    private final User author;

    @GeneratePojoBuilder
    public Comment(Long id, String body, LocalDateTime creationDate, Long newsId, User author) {
        Validation.isNotNull(body);
        Validation.isNotNull(newsId);
        Validation.isNotNull(author);

        this.id = id;
        this.body = body;
        this.creationDate = creationDate;
        this.newsId = newsId;
        this.author = author;
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
        return !(author != null ? !author.equals(comment.author) : comment.author != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (newsId != null ? newsId.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
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
                '}';
    }

    public static CommentBuilder builder() {
        return new CommentBuilder();
    }

    public static CommentBuilder fromComment(Comment comment) {
        return new CommentBuilder()
                .withId(comment.getId())
                .withBody(comment.getBody())
                .withCreationDate(comment.getCreationDate())
                .withNewsId(comment.getNewsId())
                .withAuthor(comment.getAuthor());
    }
}
