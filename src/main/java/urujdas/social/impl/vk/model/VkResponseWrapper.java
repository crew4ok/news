package urujdas.social.impl.vk.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VkResponseWrapper {
    private final VkUser[] response;

    @JsonCreator
    public VkResponseWrapper(@JsonProperty("response") VkUser[] response) {
        this.response = response;
    }

    public VkUser[] getResponse() {
        return response;
    }
}
