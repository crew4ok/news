package ru.uruydas.service;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import ru.uruydas.config.service.ServiceConfig;

import static org.mockito.Mockito.mock;

@ContextConfiguration(classes = {
        ServiceConfig.class,
        BaseServiceTest.ServiceTestConfig.class
})
public class BaseServiceTest extends AbstractTestNGSpringContextTests {

    @Configuration
    static class ServiceTestConfig {
        @Bean
        public AmazonS3 amazonS3Client() {
            return mock(AmazonS3.class);
        }
    }

}
