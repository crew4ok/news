package urujdas.service;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import urujdas.config.ServiceConfig;

@ContextConfiguration(classes = { ServiceConfig.class})
public class BaseServiceTest extends AbstractTestNGSpringContextTests {
}
