package urujdas.web.news.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import urujdas.model.Comment;

import javax.validation.constraints.NotNull;

public class CreateCommentRequest {
    @NotNull
    private final String body;

    @JsonCreator
    public CreateCommentRequest(@JsonProperty("body") String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public Comment toComment() {
        return Comment.builder()
                .withBody(body)
                .build();
    }
}
