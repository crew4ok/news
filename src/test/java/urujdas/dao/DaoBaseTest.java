package urujdas.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import urujdas.config.DaoConfig;
import urujdas.model.News;
import urujdas.model.NewsCategory;
import urujdas.model.User;

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

    protected User defaultUser;
    protected NewsCategory defaultNewsCategory;

    protected News createDefaultNews(User author, NewsCategory newsCategory) {
        News news = News.builder()
                .withTitle("title")
                .withBody("body")
                .withCreationDate(LocalDateTime.now())
                .withCategory(newsCategory)
                .withAuthor(author)
                .build();
        return newsDao.create(news);
    }

    protected News createDefaultNews(int i) {
        News news = News.builder()
                .withTitle("title" + i)
                .withBody("body")
                .withCreationDate(LocalDateTime.now())
                .withAuthor(defaultUser)
                .withCategory(defaultNewsCategory)
                .build();
        return newsDao.create(news);
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

}
