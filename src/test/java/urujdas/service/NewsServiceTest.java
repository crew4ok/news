package urujdas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import urujdas.config.ServiceConfig;
import urujdas.dao.NewsCategoryDao;
import urujdas.dao.NewsDao;
import urujdas.dao.SubscriptionDao;
import urujdas.model.LikeResult;
import urujdas.model.LikeType;
import urujdas.model.News;
import urujdas.model.NewsCategory;
import urujdas.model.User;
import urujdas.service.impl.NewsServiceImpl;
import urujdas.util.InvalidParamException;

import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

@ContextConfiguration(classes = {
        ServiceConfig.class,
        NewsServiceTest.LocalContext.class
})
public class NewsServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private NewsCategoryDao newsCategoryDao;

    @AfterMethod
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(newsDao, userService, newsCategoryDao);
        reset(newsDao, userService, newsCategoryDao);
    }

    @Test
    public void getLatestAll_hp() throws Exception {
        int latestCount = 1;
        List<News> result = Collections.singletonList(mock(News.class));

        when(newsDao.getLatestAll(latestCount)).thenReturn(result);

        List<News> latest = newsService.getLatestAll(latestCount);

        assertEquals(latest, result);
        verify(newsDao).getLatestAll(latestCount);
    }

    @Test(expectedExceptions = InvalidParamException.class)
    public void getLatestAll_latestCountIsZero() throws Exception {
        newsService.getLatestAll(0);
    }

    @Test(expectedExceptions = InvalidParamException.class)
    public void getLatestAll_latestCountIsLesserThanZero() throws Exception {
        newsService.getLatestAll(-1);
    }

    @Test
    public void getAllFromId_hp() throws Exception {
        long id = 1;
        int count = 2;
        List<News> result = Collections.singletonList(mock(News.class));

        when(newsDao.getAllFromId(id, count)).thenReturn(result);

        List<News> news = newsService.getAllFromId(id, count);

        assertEquals(news, result);
        verify(newsDao).getAllFromId(id, count);
    }

    @Test(expectedExceptions = InvalidParamException.class)
    public void getAllFromId_idIsZero() throws Exception {
        newsService.getAllFromId(0, 1);
    }

    @Test(expectedExceptions = InvalidParamException.class)
    public void getAllFromId_idIsBelowZero() throws Exception {
        newsService.getAllFromId(-1, 1);
    }

    @Test(expectedExceptions = InvalidParamException.class)
    public void getAllFromId_countIsZero() throws Exception {
        newsService.getAllFromId(1, 0);
    }

    @Test(expectedExceptions = InvalidParamException.class)
    public void getAllFromId_countIsBelowZero() throws Exception {
        newsService.getAllFromId(1, -1);
    }

    @Test
    public void create_hp() throws Exception {
        long newsCategoryId = 1L;
        User user = mock(User.class);
        NewsCategory newsCategory = mock(NewsCategory.class);
        when(newsCategory.getId()).thenReturn(newsCategoryId);

        when(userService.getCurrentUser()).thenReturn(user);
        when(newsCategoryDao.getById(newsCategoryId)).thenReturn(newsCategory);

        News news = mock(News.class);
        when(news.getCategory()).thenReturn(newsCategory);
        newsService.create(news);

        verify(newsDao).create(any(News.class));
        verify(newsCategoryDao).getById(newsCategoryId);
        verify(userService).getCurrentUser();
    }

    @Test
    public void like_like() throws Exception {
        long newsId = 1l;
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        News news = mock(News.class);

        when(userService.getCurrentUser()).thenReturn(user);
        when(newsDao.getById(newsId)).thenReturn(news);

        User liker = mock(User.class);
        when(liker.getId()).thenReturn(2L);
        when(newsDao.getLikers(news)).thenReturn(Collections.singletonList(liker));

        LikeResult likeResult = newsService.like(newsId);

        assertEquals(likeResult.getLikeType(), LikeType.LIKE);
        assertEquals(likeResult.getLikesCount(), Integer.valueOf(2));

        verify(userService).getCurrentUser();
        verify(newsDao).getById(newsId);
        verify(newsDao).getLikers(news);
        verify(newsDao).like(user, news);
    }

    @Test
    public void like_dislike() throws Exception {
        long newsId = 1l;
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        News news = mock(News.class);

        when(userService.getCurrentUser()).thenReturn(user);
        when(newsDao.getById(newsId)).thenReturn(news);

        when(newsDao.getLikers(news)).thenReturn(Collections.singletonList(user));

        LikeResult likeResult = newsService.like(newsId);

        assertEquals(likeResult.getLikeType(), LikeType.DISLIKE);
        assertEquals(likeResult.getLikesCount(), Integer.valueOf(0));

        verify(userService).getCurrentUser();
        verify(newsDao).getById(newsId);
        verify(newsDao).getLikers(news);
        verify(newsDao).dislike(user, news);
    }

    @Test(expectedExceptions = InvalidParamException.class)
    public void like_newsIdIsInvalid() throws Exception {
        newsService.like(-1L);
    }

    @Configuration
    static class LocalContext {

        @Bean
        public NewsService newsService() {
            return new NewsServiceImpl();
        }

        @Bean
        public UserService userService() {
            return mock(UserService.class);
        }

        @Bean
        public NewsDao newsDao() {
            return mock(NewsDao.class);
        }

        @Bean
        public SubscriptionDao subscriptionDao() {
            return mock(SubscriptionDao.class);
        }

        @Bean
        public NewsCategoryDao newsCategoryDao() {
            return mock(NewsCategoryDao.class);
        }
    }
}
