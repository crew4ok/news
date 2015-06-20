package urujdas.model.news;

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
    private final boolean currentUserLiked;
    private final boolean currentUserFavoured;

    @GeneratePojoBuilder
    public NewsLight(Long id, String title, String body, String username, String firstname, String lastname,
                     Integer commentsCount, Integer likesCount, boolean currentUserLiked, boolean currentUserFavoured) {
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

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public Integer getLikesCount() {
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

    public static NewsLightBuilder builder() {
        return new NewsLightBuilder();
    }

}
