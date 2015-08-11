package ru.uruydas.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import ru.uruydas.config.DaoConfig;
import ru.uruydas.model.ads.Ads;
import ru.uruydas.model.ads.AdsCategory;
import ru.uruydas.model.ads.AdsType;
import ru.uruydas.model.news.News;
import ru.uruydas.model.news.NewsCategory;
import ru.uruydas.model.users.Gender;
import ru.uruydas.model.users.GenderPreferences;
import ru.uruydas.model.users.RelationsPreferences;
import ru.uruydas.model.users.User;

import java.time.LocalDateTime;
import java.util.UUID;

@ContextConfiguration(classes = DaoConfig.class)
public abstract class DaoBaseTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    protected CommentDao commentDao;

    @Autowired
    protected NewsDao newsDao;

    @Autowired
    protected NewsCategoryDao newsCategoryDao;

    @Autowired
    protected SubscriptionDao subscriptionDao;

    @Autowired
    protected UserDao userDao;

    @Autowired
    protected DatingDao datingDao;

    @Autowired
    protected ImageDao imageDao;

    @Autowired
    protected AdsDao adsDao;

    @Autowired
    protected AdsCategoryDao adsCategoryDao;

    protected User defaultUser;
    protected NewsCategory defaultNewsCategory;

    protected News createDefaultNews(User author, NewsCategory newsCategory) {
        News news = News.builder()
                .withTitle("title")
                .withBody("body")
                .withCategory(newsCategory)
                .withAuthor(author)
                .build();
        return newsDao.create(author, news);
    }

    protected News createDefaultNews(int i) {
        News news = News.builder()
                .withTitle("title" + i)
                .withBody("body")
                .withAuthor(defaultUser)
                .withCategory(defaultNewsCategory)
                .build();
        return newsDao.create(defaultUser, news);
    }

    protected NewsCategory createDefaultNewsCategory() {
        return newsCategoryDao.create(new NewsCategory(UUID.randomUUID().toString()));
    }

    protected User createDefaultUser() {
        User user = User.builder()
                .withUsername(UUID.randomUUID().toString())
                .withPassword("password")
                .build();
        user = userDao.create(user);

        return User.fromUser(user)
                .withPassword(null)
                .build();
    }

    protected User createDefaultUser(Gender gender, GenderPreferences genderPreferences,
                                     RelationsPreferences relationsPreferences, LocalDateTime birthDate) {
        User user = User.builder()
                .withUsername(UUID.randomUUID().toString())
                .withPassword("password")
                .withGender(gender)
                .withGenderPreferences(genderPreferences)
                .withRelationsPreferences(relationsPreferences)
                .withBirthDate(birthDate)
                .build();
        user = userDao.create(user);

        return User.fromUser(user)
                .withPassword(null)
                .build();
    }

    protected AdsCategory createDefaultAdsCategory() {
        AdsCategory category = new AdsCategory("default category");
        return adsCategoryDao.create(category);
    }

    protected Ads createDefaultAds(AdsCategory adsCategory, User author) {
        Ads ads = Ads.builder()
                .withTitle("title")
                .withDescription("description")
                .withAdsType(AdsType.BUY)
                .withAuthor(author)
                .withAdsCategory(adsCategory)
                .build();
        return adsDao.create(ads);
    }
}
