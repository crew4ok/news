package urujdas.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {
        DaoConfig.class,
        ServiceConfig.class,
        WebConfig.class,
        WebSecurityConfig.class
})
@ComponentScan(basePackages = "urujdas")
public class RootConfig {
}
