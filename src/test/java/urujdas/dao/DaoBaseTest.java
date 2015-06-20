package urujdas.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import urujdas.config.DaoConfig;
import urujdas.model.Comment;
import urujdas.model.news.News;
import urujdas.model.news.NewsCategory;
import urujdas.model.users.User;

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

    protected News createNews(String title, User author) {
        NewsCategory newsCategory = createDefaultNewsCategory();

        return createNews(title, "body", author, newsCategory, 0, 0);
    }

    protected News createNews(String title, String body, User author, NewsCategory newsCategory,
                              int commentsCount, int likesCount) {
        News news = News.builder()
                .withTitle(title)
                .withBody(body)
                .withCreationDate(LocalDateTime.now())
                .withCategory(newsCategory)
                .withAuthor(author)
                .build();

        news = newsDao.create(news);

        for (int i = 0; i < commentsCount; i++) {
            User commenter = createDefaultUser();

            Comment comment = Comment.builder()
                    .withBody("body")
                    .withCreationDate(LocalDateTime.now())
                    .withAuthor(commenter)
                    .withNewsId(news.getId())
                    .build();

            commentDao.create(comment);
        }

        for (int i = 0; i < likesCount; i++) {
            User liker = createDefaultUser();

            newsDao.like(liker, news);
        }

        return newsDao.getById(news.getId());
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
