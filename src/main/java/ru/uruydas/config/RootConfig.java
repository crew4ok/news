package ru.uruydas.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.uruydas.config.dao.DaoConfig;
import ru.uruydas.config.security.WebSecurityConfig;
import ru.uruydas.config.service.ServiceConfig;
import ru.uruydas.config.social.SocialConfig;
import ru.uruydas.config.web.WebConfig;

@Configuration
@Import(value = {
        DaoConfig.class,
        ServiceConfig.class,
        SocialConfig.class,
        WebConfig.class,
        WebSecurityConfig.class
})
@ComponentScan(basePackages = "ru.uruydas")
public class RootConfig {
}
