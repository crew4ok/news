package urujdas.social.impl.vk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import urujdas.model.social.SocialNetworkType;
import urujdas.social.impl.vk.model.VkResponseWrapper;
import urujdas.social.impl.vk.model.VkUser;
import urujdas.social.service.SocialNetworkApi;

public class VKApi implements SocialNetworkApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(VKApi.class);

    private static final String API_URL = "https://api.vk.com/";
    private static final String GET_USER_METHOD = "users.get";
    private static final String API_VERSION = "5.35";

    private static final String ACCESS_TOKEN_PARAM = "access_token";

    private static final String USER_FIELDS_PARAM = "fields";
    private static final String USER_FIELDS = String.join(",",
            "id", "first_name", "last_name", "sex", "country", "city", "bday", "bday_visibility"
    );

    private final RestTemplate restTemplate;
    private final UriComponents requestTemplate;

    public VKApi() {
        this.restTemplate = new RestTemplate();
        this.requestTemplate = UriComponentsBuilder.fromHttpUrl(API_URL)
                .pathSegment("method")
                .queryParam("v", API_VERSION)
                .build();
    }

    @Override
    public VkUser getUser(SocialNetworkType socialNetworkType, String accessToken) {
        try {
            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .uriComponents(requestTemplate)
                    .pathSegment(GET_USER_METHOD)
                    .queryParam(ACCESS_TOKEN_PARAM, accessToken)
                    .queryParam(USER_FIELDS_PARAM, USER_FIELDS)
                    .build();

            ResponseEntity<VkResponseWrapper> vkUserEntity = restTemplate.getForEntity(
                    uriComponents.toUri(), VkResponseWrapper.class
            );

            if (vkUserEntity.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Status code: " + vkUserEntity.getStatusCode());
            }

            return vkUserEntity.getBody().getResponse()[0];
        } catch (Exception e) {
            LOGGER.error("Error while retrieving VK user id", e);
            throw new RuntimeException(e);
        }
    }
}
