package urujdas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import urujdas.model.social.SocialNetworkType;
import urujdas.social.impl.SocialNetworkApiDelegate;
import urujdas.social.impl.SocialNetworkModelConverterDelegate;
import urujdas.social.impl.vk.VKApi;
import urujdas.social.impl.vk.VkModelConverter;
import urujdas.social.service.SocialNetworkApi;
import urujdas.social.service.SocialNetworkModelConverter;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class SocialConfig {

    @Bean
    public SocialNetworkApi socialNetworkApi() {
        Map<SocialNetworkType, SocialNetworkApi> apis = new EnumMap<>(SocialNetworkType.class);
        apis.put(SocialNetworkType.VK, new VKApi());

        return new SocialNetworkApiDelegate(apis);
    }

    @Bean
    public SocialNetworkModelConverter socialNetworkModelConverter() {
        Map<SocialNetworkType, SocialNetworkModelConverter> converters = new EnumMap<>(SocialNetworkType.class);
        converters.put(SocialNetworkType.VK, new VkModelConverter());

        return new SocialNetworkModelConverterDelegate(converters);
    }
}
