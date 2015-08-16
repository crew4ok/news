package ru.uruydas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.uruydas.common.util.exception.InvalidParamException;
import ru.uruydas.favourites.model.FavourResult;
import ru.uruydas.images.dao.ImageDao;
import ru.uruydas.likes.model.LikeResult;
import ru.uruydas.likes.model.LikeType;
import ru.uruydas.news.dao.NewsCategoryDao;
import ru.uruydas.news.dao.NewsDao;
import ru.uruydas.news.model.News;
import ru.uruydas.news.model.NewsCategory;
import ru.uruydas.news.service.NewsService;
import ru.uruydas.news.service.impl.NewsServiceImpl;
import ru.uruydas.subscriptions.dao.SubscriptionDao;
import ru.uruydas.users.model.User;
import ru.uruydas.users.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

@ContextConfiguration(classes = NewsServiceTest.LocalContext.class)
public class NewsServiceTest extends BaseServiceTest {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private NewsCategoryDao newsCategoryDao;

    @Autowired
    private ImageDao imageDao;

    private User currentUser;

    @BeforeMethod
    public void setUp() throws Exception {
        this.currentUser = mock(User.class);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(newsDao, userService, newsCategoryDao);
        reset(newsDao, userService, newsCategoryDao);
    }

    @Test
    public void getLatestAll_hp() throws Exception {
        int latestCount = 1;
        List<News> result = Collections.singletonList(mock(News.class));

        when(newsDao.getLatestAll(currentUser, latestCount)).thenReturn(result);

        when(userService.getCurrentUser()).thenReturn(currentUser);

        newsService.getLatestAll(latestCount);

        verify(newsDao).getLatestAll(currentUser, latestCount);
        verify(userService).getCurrentUser();
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

        when(newsDao.getAllFromId(currentUser, id, count)).thenReturn(result);
        when(imageDao.getByUser(any(User.class))).thenReturn(Optional.empty());

        when(userService.getCurrentUser()).thenReturn(currentUser);

        newsService.getAllFromId(id, count);

        verify(newsDao).getAllFromId(currentUser, id, count);
        verify(userService).getCurrentUser();
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
        NewsCategory newsCategory = mock(NewsCategory.class);
        when(newsCategory.getId()).thenReturn(newsCategoryId);

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(newsCategoryDao.getById(newsCategoryId)).thenReturn(newsCategory);

        News news = mock(News.class);
        when(news.getCategory()).thenReturn(newsCategory);
        newsService.create(news);

        verify(newsDao).create(eq(currentUser), any(News.class));
        verify(newsCategoryDao).getById(newsCategoryId);
        verify(userService).getCurrentUser();
    }

    @Test
    public void favour_favour() throws Exception {
        User currentUser = mock(User.class);
        when(userService.getCurrentUser()).thenReturn(currentUser);

        long newsId = 1L;
        News news = mock(News.class);
        when(newsDao.getById(currentUser, newsId)).thenReturn(news);

        when(newsDao.getAllFavourites(currentUser)).thenReturn(Collections.singletonList(mock(News.class)));

        FavourResult favourResult = newsService.favour(newsId);

        assertEquals(favourResult, FavourResult.FAVOUR);

        verify(userService).getCurrentUser();
        verify(newsDao).getById(currentUser, newsId);
        verify(newsDao).getAllFavourites(currentUser);
        verify(newsDao).favour(currentUser, news);
    }

    @Test
    public void favour_unfavour() throws Exception {
        User currentUser = mock(User.class);
        when(userService.getCurrentUser()).thenReturn(currentUser);

        long newsId = 1L;
        News news = mock(News.class);
        when(newsDao.getById(currentUser, newsId)).thenReturn(news);

        when(newsDao.getAllFavourites(currentUser)).thenReturn(Collections.singletonList(news));

        FavourResult favourResult = newsService.favour(newsId);

        assertEquals(favourResult, FavourResult.UNFAVOUR);

        verify(userService).getCurrentUser();
        verify(newsDao).getById(currentUser, newsId);
        verify(newsDao).getAllFavourites(currentUser);
        verify(newsDao).unfavour(currentUser, news);
    }

    @Test
    public void like_like() throws Exception {
        long newsId = 1l;
        when(currentUser.getId()).thenReturn(1L);
        News news = mock(News.class);

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(newsDao.getById(currentUser, newsId)).thenReturn(news);

        User liker = mock(User.class);
        when(liker.getId()).thenReturn(2L);
        when(newsDao.getLikers(news)).thenReturn(Collections.singletonList(liker));

        LikeResult likeResult = newsService.like(newsId);

        assertEquals(likeResult.getLikeType(), LikeType.LIKE);
        assertEquals(likeResult.getLikesCount(), Integer.valueOf(2));

        verify(userService).getCurrentUser();
        verify(newsDao).getById(currentUser, newsId);
        verify(newsDao).getLikers(news);
        verify(newsDao).like(currentUser, news);
    }

    @Test
    public void like_dislike() throws Exception {
        long newsId = 1l;
        when(currentUser.getId()).thenReturn(1L);
        News news = mock(News.class);

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(newsDao.getById(currentUser, newsId)).thenReturn(news);

        when(newsDao.getLikers(news)).thenReturn(Collections.singletonList(currentUser));

        LikeResult likeResult = newsService.like(newsId);

        assertEquals(likeResult.getLikeType(), LikeType.DISLIKE);
        assertEquals(likeResult.getLikesCount(), Integer.valueOf(0));

        verify(userService).getCurrentUser();
        verify(newsDao).getById(currentUser, newsId);
        verify(newsDao).getLikers(news);
        verify(newsDao).dislike(currentUser, news);
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

        @Bean
        public ImageDao imageDao() {
            return mock(ImageDao.class);
        }
    }
}
