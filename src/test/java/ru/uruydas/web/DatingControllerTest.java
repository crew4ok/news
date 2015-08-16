package ru.uruydas.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.uruydas.common.service.exception.PullUpTooFrequentException;
import ru.uruydas.common.web.CommonExceptionHandler;
import ru.uruydas.common.web.WebCommons;
import ru.uruydas.config.web.WebConfig;
import ru.uruydas.dating.service.DatingService;
import ru.uruydas.dating.web.DatingController;
import ru.uruydas.users.model.AgeRange;
import ru.uruydas.users.model.Gender;
import ru.uruydas.users.model.GenderPreferences;
import ru.uruydas.users.model.RelationsPreferences;
import ru.uruydas.users.model.UserFilter;
import ru.uruydas.users.web.model.UserFilterRequest;

import java.time.LocalDateTime;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ContextConfiguration(classes = {
        WebConfig.class,
        DatingControllerTest.LocalContext.class
})
public class DatingControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DatingService datingService;

    @AfterMethod
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(datingService);
        reset(datingService);
    }

    @Test
    public void getLatestUsersByFilter_hp() throws Exception {
        UserFilterRequest request = UserFilterRequest.builder()
                .withGender(Gender.FEMALE)
                .withGenderPreferences(GenderPreferences.FEMALES)
                .withRelationsPreferences(RelationsPreferences.INTERESTS_RELATIONS)
                .withAgeRange(new AgeRange(1, 2))
                .build();

        String requestInJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post(WebCommons.VERSION_PREFIX + "/users/dating")
                .content(requestInJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(datingService).getLatestUsersByFilter(
                eq(request.toUserFilter()),
                eq(WebCommons.PAGING_COUNT)
        );
    }

    @Test
    public void getUsersByFilterFromDate_hp() throws Exception {
        LocalDateTime pullUpDate = LocalDateTime.now();

        UserFilterRequest request = UserFilterRequest.builder()
                .withGender(Gender.FEMALE)
                .withGenderPreferences(GenderPreferences.FEMALES)
                .withRelationsPreferences(RelationsPreferences.INTERESTS_RELATIONS)
                .withAgeRange(new AgeRange(1, 2))
                .withPullUpDate(pullUpDate)
                .build();

        String requestInJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post(WebCommons.VERSION_PREFIX + "/users/dating")
                .content(requestInJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(datingService).getUsersByFilterFromDate(
                eq(request.toUserFilter()),
                eq(pullUpDate),
                eq(WebCommons.PAGING_COUNT)
        );
    }

    @Test
    public void getLastFilter_hp() throws Exception {
        UserFilter filter = UserFilter.builder()
                .withGender(Gender.MALE)
                .build();

        when(datingService.findCurrentUserFilter()).thenReturn(filter);

        mockMvc.perform(get(WebCommons.VERSION_PREFIX + "/users/dating/last_filter"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("gender").value(filter.getGender().name()));

        verify(datingService).findCurrentUserFilter();
    }

    @Test
    public void getLastFilter_noFilter() throws Exception {
        when(datingService.findCurrentUserFilter()).thenReturn(null);

        mockMvc.perform(get(WebCommons.VERSION_PREFIX + "/users/dating/last_filter"))
                .andExpect(status().isNoContent());

        verify(datingService).findCurrentUserFilter();
    }

    @Test
    public void pullUp_hp() throws Exception {
        mockMvc.perform(post(WebCommons.VERSION_PREFIX + "/users/dating/pull_up/"))
                .andExpect(status().isOk());

        verify(datingService).pullCurrentUserUp();
    }

    @Test
    public void pullUp_tooFrequent() throws Exception {
        doThrow(new PullUpTooFrequentException(LocalDateTime.now())).when(datingService).pullCurrentUserUp();

        mockMvc.perform(post(WebCommons.VERSION_PREFIX + "/users/dating/pull_up/"))
                .andExpect(status().isBadRequest());

        verify(datingService).pullCurrentUserUp();
    }

    @Configuration
    static class LocalContext {
        @Bean
        public DatingController datingController() {
            return new DatingController();
        }

        @Bean
        public CommonExceptionHandler commonExceptionHandler() {
            return new CommonExceptionHandler();
        }

        @Bean
        public MockMvc mockMvc(WebApplicationContext ctx) {
            return MockMvcBuilders.webAppContextSetup(ctx)
                    .build();
        }

        @Bean
        public DatingService datingService() {
            return mock(DatingService.class);
        }
    }

}
