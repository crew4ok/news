package ru.uruydas.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import ru.uruydas.ads.dao.AdsCategoryDao;
import ru.uruydas.ads.dao.AdsDao;
import ru.uruydas.ads.dao.AdsTypeDao;
import ru.uruydas.ads.model.Ads;
import ru.uruydas.ads.model.AdsCategory;
import ru.uruydas.ads.model.AdsType;
import ru.uruydas.comments.dao.CommentDao;
import ru.uruydas.config.dao.DaoConfig;
import ru.uruydas.dating.dao.DatingDao;
import ru.uruydas.images.dao.ImageDao;
import ru.uruydas.news.dao.NewsCategoryDao;
import ru.uruydas.news.dao.NewsDao;
import ru.uruydas.news.model.News;
import ru.uruydas.news.model.NewsCategory;
import ru.uruydas.subscriptions.dao.SubscriptionDao;
import ru.uruydas.users.dao.UserDao;
import ru.uruydas.users.model.Gender;
import ru.uruydas.users.model.GenderPreferences;
import ru.uruydas.users.model.RelationsPreferences;
import ru.uruydas.users.model.User;

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

    @Autowired
    protected AdsTypeDao adsTypeDao;

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

    protected AdsType createDefaultAdsType(AdsCategory adsCategory) {
        AdsType adsType = new AdsType("default ads type", adsCategory.getId());
        return adsTypeDao.create(adsType);
    }

    protected Ads createDefaultAds(AdsCategory adsCategory, AdsType adsType, User author) {
        Ads ads = Ads.builder()
                .withTitle("title")
                .withDescription("description")
                .withAdsType(adsType)
                .withAuthor(author)
                .withAdsCategory(adsCategory)
                .build();
        return adsDao.create(ads);
    }
}
