package ru.uruydas.config.social;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.uruydas.social.impl.SocialNetworkApiDelegate;
import ru.uruydas.social.impl.SocialNetworkModelConverterDelegate;
import ru.uruydas.social.impl.vk.VKApi;
import ru.uruydas.social.impl.vk.VkModelConverter;
import ru.uruydas.social.model.SocialNetworkType;
import ru.uruydas.social.service.SocialNetworkApi;
import ru.uruydas.social.service.SocialNetworkModelConverter;

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
