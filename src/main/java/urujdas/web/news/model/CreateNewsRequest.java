package urujdas.web.news.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import urujdas.model.news.News;
import urujdas.model.news.NewsCategory;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CreateNewsRequest {

    @NotNull
    private final String title;

    @NotNull
    private final String body;

    private final String location;

    @NotNull
    private final Long categoryId;

    private final List<Long> imageIds;

    @GeneratePojoBuilder
    @JsonCreator
    public CreateNewsRequest(@JsonProperty("title") String title,
                             @JsonProperty("body") String body,
                             @JsonProperty("location") String location,
                             @JsonProperty("categoryId") Long categoryId,
                             @JsonProperty("imageIds") List<Long> imageIds) {
        this.title = title;
        this.body = body;
        this.location = location;
        this.categoryId = categoryId;
        this.imageIds = imageIds;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getLocation() {
        return location;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public List<Long> getImageIds() {
        return imageIds;
    }

    public News toNews() {
        return News.builder()
                .withTitle(title)
                .withBody(body)
                .withLocation(location)
                .withCategory(new NewsCategory(categoryId, null))
                .withImageIds(imageIds)
                .build();
    }

    public static CreateNewsRequestBuilder builder() {
        return new CreateNewsRequestBuilder();
    }
}
