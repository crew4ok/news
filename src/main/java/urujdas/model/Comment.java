package urujdas.model;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.time.LocalDateTime;

public class Comment {
    private final Long id;
    private final String body;
    private final LocalDateTime creationDate;
    private final Long newsId;
    private final User author;

    @GeneratePojoBuilder
    public Comment(Long id, String body, LocalDateTime creationDate, Long newsId, User author) {
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
