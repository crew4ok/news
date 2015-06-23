package urujdas.web.news.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import urujdas.model.comments.Comment;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CreateCommentRequest {
    @NotNull
    private final String body;

    private final List<Long> imageIds;

    @JsonCreator
    public CreateCommentRequest(@JsonProperty("body") String body,
                                @JsonProperty("imageIds") List<Long> imageIds) {
        this.body = body;
        this.imageIds = imageIds;
    }

    public String getBody() {
        return body;
    }

    public List<Long> getImageIds() {
        return imageIds;
    }

    public Comment toComment() {
        return Comment.builder()
                .withBody(body)
                .withImageIds(imageIds)
                .build();
    }
}
