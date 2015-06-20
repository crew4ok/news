package urujdas.dao;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import urujdas.dao.exception.NotFoundException;
import urujdas.model.news.News;
import urujdas.model.news.NewsCategory;
import urujdas.model.subscriptions.Subscription;
import urujdas.model.users.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class NewsDaoTest extends DaoBaseTest {
    @BeforeMethod
    public void setup() {
        this.defaultUser = createDefaultUser();
        this.defaultNewsCategory = createDefaultNewsCategory();
    }

    /*
    *
    * NewsDao.getById
    *
    */

    @Test
    public void getById_hp() throws Exception {
        News news = News.builder()
                .withTitle("title")
                .withBody("body")
                .withLocation("location")
                .withLikesCount(1)
                .withAuthor(defaultUser)
                .withCategory(defaultNewsCategory)
                .build();

        Long id = newsDao.create(news).getId();

        News actualNews = newsDao.getById(id);

        // likes should not be set
        assertEquals(actualNews.getLikesCount(), Integer.valueOf(0));

        assertEquals(actualNews.getTitle(), news.getTitle());
        assertEquals(actualNews.getBody(), news.getBody());
        assertNotNull(actualNews.getCreationDate());
        assertEquals(actualNews.getLocation(), news.getLocation());
        assertEquals(actualNews.getAuthor(), defaultUser);
        assertEquals(actualNews.getCategory(), defaultNewsCategory);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void getById_notFound() throws Exception {
        newsDao.getById(-1L);
    }

    /*
    *
    * NewsDao.getLatestAll
    *
    */

    @Test
    public void getLatestAll_hp() {
        int totalCount = 5;
        int latestCount = 2;

        for (int i = 1; i <= totalCount; i++) {
            createDefaultNews(i);
        }

        List<News> latestNews = newsDao.getLatestAll(latestCount);

        assertEquals(latestNews.size(), 2);

        System.out.println(latestNews);

        News first = latestNews.get(0);
        News second = latestNews.get(1);

        assertEquals(first.getTitle(), "title5");
        assertEquals(second.getTitle(), "title4");
    }

    @Test
    public void getLatestAll_newsCountIsLesserThanRequested() throws Exception {
        createDefaultNews(0);

        List<News> latest = newsDao.getLatestAll(2);

        assertEquals(latest.size(), 1);

        News first = latest.get(0);

        assertEquals(first.getTitle(), "title0");
    }

    @Test
    public void getLatestAll_empty() throws Exception {
        List<News> latest = newsDao.getLatestAll(100);

        assertEquals(latest.size(), 0);
    }

    /*
    *
    * NewsDao.getAllFromId
    *
    */

    @Test
    public void getAllFromId_hp() throws Exception {
        int totalCount = 10;
        int to = 6;
        int count = 5;

        for (int i = 1; i <= totalCount; i++) {
            createDefaultNews(i);
        }

        List<Long> allIds = newsDao.getLatestAll(100).stream()
                .map(News::getId)
                .sorted()
                .collect(Collectors.toList());

        List<Long> expectedIds = allIds.subList(to - count, to);

        List<News> news = newsDao.getAllFromId(expectedIds.get(expectedIds.size() - 1), count);

        assertEquals(news.size(), count);

        Collections.sort(news, (News n1, News n2) -> n1.getId().compareTo(n2.getId()));

        for (int i = 0; i < news.size(); i++) {
            News actualNews = news.get(i);
            Long expectedId = expectedIds.get(i);

            assertEquals(actualNews.getId(), expectedId);
            assertEquals(actualNews.getBody(), "body");
            assertNotNull(actualNews.getCreationDate());
        }
    }

    /*
    *
    * NewsDao.getLatestByUser
    *
    */

    @Test
    public void getLatestByUser_hp() throws Exception {
        int totalCount = 5;
        int latestCount = 2;

        List<News> news = IntStream.range(0, totalCount)
                .mapToObj(this::createDefaultNews)
                .collect(Collectors.toList());

        List<News> latestNews = news.subList(news.size() - latestCount, news.size());
        Collections.reverse(latestNews);

        List<News> actualLatestNews = newsDao.getLatestByUser(defaultUser, latestCount);

        assertEquals(actualLatestNews, latestNews);
    }

    @Test
    public void getLatestByUser_newsCountIsLesserThanRequested() throws Exception {
        int totalCount = 3;
        int latestCount = 5;

        List<News> news = IntStream.range(0, totalCount)
                .mapToObj(this::createDefaultNews)
                .collect(Collectors.toList());

        Collections.reverse(news);

        List<News> actualLatestNews = newsDao.getLatestByUser(defaultUser, latestCount);

        assertEquals(actualLatestNews, news);
    }

    @Test
    public void getLatestByUser_noNews() throws Exception {
        List<News> latestNews = newsDao.getLatestByUser(defaultUser, 5);

        assertTrue(latestNews.isEmpty());
    }

    /*
    *
    * NewsDao.getByUserFromId
    *
    */

    @Test
    public void getByUserFromId_hp() throws Exception {
        int totalCount = 10;
        int from = 3;
        int count = 5;

        List<News> news = IntStream.range(0, totalCount)
                .mapToObj(this::createDefaultNews)
                .collect(Collectors.toList());

        Collections.reverse(news);

        List<News> expectedNews = news.subList(from, from + count);

        Long fromId = expectedNews.get(0).getId();

        List<News> actualNews = newsDao.getByUserFromId(defaultUser, fromId, count);

        assertEquals(actualNews, expectedNews);
    }

    @Test
    public void getByUserFromId_newsCountIsLesserThanRequested() throws Exception {
        int totalCount = 5;
        int from = 3;
        int count = 5;

        List<News> news = IntStream.range(0, totalCount)
                .mapToObj(this::createDefaultNews)
                .collect(Collectors.toList());

        Collections.reverse(news);

        List<News> expectedNews = news.subList(from, news.size());

        Long fromId = expectedNews.get(0).getId();

        List<News> actualNews = newsDao.getByUserFromId(defaultUser, fromId, count);

        assertEquals(actualNews, expectedNews);
    }

    @Test
    public void getByUserFromId_noNews() throws Exception {
        List<News> news = newsDao.getByUserFromId(defaultUser, 1L, 10);

        assertTrue(news.isEmpty());
    }

    /*
    *
    * NewsDao.getLatestBySubscription
    *
    */

    @Test
    public void getLatestBySubscription_hp() throws Exception {
        User subscriber = createDefaultUser();

        User firstAuthor = createDefaultUser();
        User secondAuthor = createDefaultUser();

        subscriptionDao.create(subscriber, firstAuthor);
        subscriptionDao.create(subscriber, secondAuthor);

        News firstNews = createDefaultNews(firstAuthor, defaultNewsCategory);
        News secondNews = createDefaultNews(secondAuthor, defaultNewsCategory);

        Subscription subscription = subscriptionDao.getByUser(subscriber);

        List<News> newsBySubscription = newsDao.getLatestBySubscription(subscription, 100);

        assertEquals(newsBySubscription.size(), 2);
        assertEquals(newsBySubscription, Arrays.asList(secondNews, firstNews));
    }

    @Test
    public void getLatestBySubscription_noSubscription() throws Exception {
        User subscriber = createDefaultUser();

        createDefaultNews(subscriber, defaultNewsCategory);
        createDefaultNews(subscriber, defaultNewsCategory);

        Subscription subscription = subscriptionDao.getByUser(subscriber);

        List<News> newsBySubscription = newsDao.getLatestBySubscription(subscription, 100);

        assertTrue(newsBySubscription.isEmpty());
    }

    @Test
    public void getLatestBySubscription_noNews() throws Exception {
        User subscriber = createDefaultUser();

        User firstAuthor = createDefaultUser();
        User secondAuthor = createDefaultUser();

        subscriptionDao.create(subscriber, firstAuthor);
        subscriptionDao.create(subscriber, secondAuthor);

        Subscription subscription = subscriptionDao.getByUser(subscriber);

        List<News> newsBySubscription = newsDao.getLatestBySubscription(subscription, 100);

        assertTrue(newsBySubscription.isEmpty());
    }

    /*
    *
    * NewsDao.getBySubscriptionFromId
    *
    */

    @Test
    public void getBySubscriptionFromId_hp() throws Exception {
        int totalCountPerAuthor = 5;
        int from = 3;
        int count = 5;

        User firstAuthor = createDefaultUser();
        User secondAuthor = createDefaultUser();

        subscriptionDao.create(defaultUser, firstAuthor);
        subscriptionDao.create(defaultUser, secondAuthor);

        List<News> news = new ArrayList<>();
        for (int i = 0; i < totalCountPerAuthor * 2; i++) {
            News firstAuthorNews = createDefaultNews(firstAuthor, defaultNewsCategory);
            News secondAuthorNews = createDefaultNews(secondAuthor, defaultNewsCategory);

            news.add(firstAuthorNews);
            news.add(secondAuthorNews);
        }
        Collections.reverse(news);
        Long fromId = news.get(from).getId();
        List<News> expectedNews = news.subList(from, from + count);

        Subscription subscription = subscriptionDao.getByUser(defaultUser);
        List<News> actualNews = newsDao.getBySubscriptionFromId(subscription, fromId, count);

        assertEquals(actualNews, expectedNews);
    }

    @Test
    public void getBySubscriptionFromId_newsCountIsLesserThanRequested() throws Exception {
        int totalCountPerAuthor = 2;
        int from = 3;
        int count = 6;

        User firstAuthor = createDefaultUser();
        User secondAuthor = createDefaultUser();

        subscriptionDao.create(defaultUser, firstAuthor);
        subscriptionDao.create(defaultUser, secondAuthor);

        List<News> news = new ArrayList<>();
        for (int i = 0; i < totalCountPerAuthor * 2; i++) {
            News firstAuthorNews = createDefaultNews(firstAuthor, defaultNewsCategory);
            News secondAuthorNews = createDefaultNews(secondAuthor, defaultNewsCategory);

            news.add(firstAuthorNews);
            news.add(secondAuthorNews);
        }
        Collections.reverse(news);
        Long fromId = news.get(from).getId();
        List<News> expectedNews = news.subList(from, news.size());

        Subscription subscription = subscriptionDao.getByUser(defaultUser);
        List<News> actualNews = newsDao.getBySubscriptionFromId(subscription, fromId, count);

        assertEquals(actualNews, expectedNews);
    }

    @Test
    public void getBySubscriptionFromId_noSubscription() throws Exception {
        Subscription subscription = subscriptionDao.getByUser(defaultUser);
        List<News> news = newsDao.getBySubscriptionFromId(subscription, 1L, 10);

        assertTrue(news.isEmpty());
    }

    /*
    *
    * NewsDao.getLatestFavourites
    *
    */

    @Test
    public void getLatestFavourites_hp() throws Exception {
        int totalCount = 5;
        int count = 3;

        List<News> favourites = new ArrayList<>();
        for (int i = 0; i < totalCount; i++) {
            News news = createDefaultNews(defaultUser, defaultNewsCategory);
            if (i % 2 == 0) {
                newsDao.favour(defaultUser, news);
                favourites.add(news);
            }
        }
        Collections.reverse(favourites);

        List<News> expectedFavourites = favourites.subList(0, count);

        List<News> actualFavourites = newsDao.getLatestFavourites(defaultUser, count);

        assertEquals(actualFavourites, expectedFavourites);
    }

    @Test
    public void getLatestFavourites_noFavourites() throws Exception {
        List<News> latestFavourites = newsDao.getLatestFavourites(defaultUser, 10);

        assertTrue(latestFavourites.isEmpty());
    }

    /*
    *
    * NewsDao.getFavouritesFromId
    *
    */

    @Test
    public void getFavouritesFromId_hp() throws Exception {
        int totalCount = 12;
        int count = 3;
        int from = 3;

        List<News> favourites = new ArrayList<>();
        for (int i = 0; i < totalCount; i++) {
            News news = createDefaultNews(defaultUser, defaultNewsCategory);
            if (i % 2 == 0) {
                newsDao.favour(defaultUser, news);
                favourites.add(news);
            }
        }
        Collections.reverse(favourites);

        List<News> expectedFavourites = favourites.subList(from, from + count);
        Long fromId = expectedFavourites.get(0).getId();

        List<News> actualFavourites = newsDao.getFavouritesFromId(defaultUser, fromId, count);

        assertEquals(actualFavourites, expectedFavourites);
    }

    /*
    *
    * NewsDao.favour
    *
    */

    @Test
    public void favour_hp() throws Exception {
        User user = createDefaultUser();

        News firstNews = createDefaultNews(defaultUser, defaultNewsCategory);
        News secondNews = createDefaultNews(defaultUser, defaultNewsCategory);

        newsDao.favour(user, firstNews);
        newsDao.favour(user, secondNews);

        List<News> favourites = newsDao.getAllFavourites(user);

        assertEquals(favourites, Arrays.asList(secondNews, firstNews));
    }

    /*
    *
    * NewsDao.unfavour
    *
    */

    @Test
    public void unfavour_hp() throws Exception {
        User user = createDefaultUser();

        News firstNews = createDefaultNews(defaultUser, defaultNewsCategory);
        News secondNews = createDefaultNews(defaultUser, defaultNewsCategory);

        newsDao.favour(user, firstNews);
        newsDao.favour(user, secondNews);

        newsDao.unfavour(user, firstNews);

        List<News> favourites = newsDao.getAllFavourites(user);

        assertEquals(favourites, Collections.singletonList(secondNews));

        newsDao.unfavour(user, secondNews);

        assertTrue(newsDao.getAllFavourites(user).isEmpty());
    }

    /*
    *
    * NewsDao.like
    *
    */

    @Test
    public void like_hp() throws Exception {
        NewsCategory category = createDefaultNewsCategory();

        User firstUser = createDefaultUser();
        User secondUser = createDefaultUser();

        News firstNews = createDefaultNews(firstUser, category);
        News secondNews = createDefaultNews(secondUser, category);

        newsDao.like(firstUser, firstNews);
        newsDao.like(secondUser, firstNews);
        newsDao.like(secondUser, secondNews);

        List<User> firstNewsLikers = newsDao.getLikers(firstNews);
        assertEquals(firstNewsLikers.size(), 2);
        assertTrue(firstNewsLikers.contains(firstUser));
        assertTrue(firstNewsLikers.contains(secondUser));

        List<User> secondNewsLikers = newsDao.getLikers(secondNews);
        assertEquals(secondNewsLikers.size(), 1);
        assertTrue(secondNewsLikers.contains(secondUser));

        News actualFirstNews = newsDao.getById(firstNews.getId());
        assertEquals(actualFirstNews.getLikesCount(), Integer.valueOf(2));

        News actualSecondNews = newsDao.getById(secondNews.getId());
        assertEquals(actualSecondNews.getLikesCount(), Integer.valueOf(1));
    }

    /*
    *
    * NewsDao.dislike
    *
    */


    @Test
    public void dislike_hp() throws Exception {
        NewsCategory category = createDefaultNewsCategory();
        User user = createDefaultUser();
        News news = createDefaultNews(user, category);

        newsDao.like(user, news);

        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                newsDao.dislike(user, news);
            } else {
                newsDao.like(user, news);
            }
        }

        List<User> likers = newsDao.getLikers(news);
        assertTrue(likers.isEmpty());

        News actualNews = newsDao.getById(news.getId());
        assertEquals(actualNews.getLikesCount(), Integer.valueOf(0));
    }
}
