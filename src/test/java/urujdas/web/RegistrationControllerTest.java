package urujdas.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import urujdas.config.WebConfig;
import urujdas.model.User;
import urujdas.service.UserService;
import urujdas.service.exception.UserAlreadyExistsException;
import urujdas.web.common.WebCommons;
import urujdas.web.user.model.RegisterUserRequest;
import urujdas.web.user.RegistrationController;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, RegistrationControllerTest.LocalContext.class })
@WebAppConfiguration
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(userService);
        reset(userService);
    }

    @Test
    public void register_hp() throws Exception {
        RegisterUserRequest registerUserRequest = RegisterUserRequest.builder()
                .withUsername("username")
                .withPassword("password")
                .build();

        String requestInJson = toJson(registerUserRequest);

        mockMvc.perform(post(WebCommons.VERSION_PREFIX + "/register")
                .content(requestInJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(userService).register(any(User.class));
    }

    @Test
    public void register_alreadyRegistered() throws Exception{
        RegisterUserRequest registerUserRequest = RegisterUserRequest.builder()
                .withUsername("username")
                .withPassword("password")
                .build();

        String requestInJson = toJson(registerUserRequest);

        doThrow(new UserAlreadyExistsException()).when(userService).register(any(User.class));

        mockMvc.perform(post(WebCommons.VERSION_PREFIX + "/register")
                .content(requestInJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("errorType").value("USER_ALREADY_EXISTS"));

        verify(userService).register(any(User.class));
    }

    private String toJson(Object value) throws Exception{
        return objectMapper.writeValueAsString(value);
    }

    @Configuration
    static class LocalContext {
        @Bean
        public RegistrationController registrationController() {
            return new RegistrationController();
        }

        @Bean
        public MockMvc mockMvc(WebApplicationContext ctx) {
            return MockMvcBuilders.webAppContextSetup(ctx)
                    .build();
        }

        @Bean
        public UserService userService() {
            return mock(UserService.class);
        }
    }

}
