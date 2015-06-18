package urujdas.model;

import net.karneim.pojobuilder.GeneratePojoBuilder;

public class NewsLight {
    private final Long id;
    private final String title;
    private final String body;
    private final String username;
    private final String firstname;
    private final String lastname;
    private final Integer commentsCount;
    private final Integer likesCount;

    @GeneratePojoBuilder
    public NewsLight(Long id, String title, String body, String username, String firstname, String lastname,
                     Integer commentsCount, Integer likesCount) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.commentsCount = commentsCount;
        this.likesCount = likesCount;
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

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public static NewsLightBuilder builder() {
        return new NewsLightBuilder();
    }

    public String getBody() {
        return body;
    }
}
