package urujdas.dao;

import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;
import urujdas.config.DaoConfig;
import urujdas.dao.impl.SubscriptionDaoImpl;
import urujdas.dao.impl.UserDaoImpl;
import urujdas.model.Subscription;
import urujdas.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@ContextConfiguration(classes = {
        DaoConfig.class,
        SubscriptionDaoTest.LocalContext.class
})
public class SubscriptionDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private SubscriptionDao subscriptionDao;

    @Autowired
    private UserDao userDao;

    @Test
    public void getSubscription_hp() throws Exception {
        User subscriber = createDefaultUser();

        List<User> authors = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            User author = createDefaultUser();
            authors.add(author);

            subscriptionDao.create(subscriber, author);
        }

        Subscription subscription = subscriptionDao.getByUser(subscriber);

        assertEquals(subscriber, subscription.getSubscriber());

        Comparator<User> comparator = (u1, u2) -> u1.getId().compareTo(u2.getId());
        Collections.sort(authors, comparator);
        Collections.sort(subscription.getAuthors(), comparator);

        assertEquals(subscription.getAuthors(), authors);
    }

    @Test
    public void getSubscription_noSubscription() throws Exception {
        User subscriber = createDefaultUser();

        Subscription subscription = subscriptionDao.getByUser(subscriber);

        assertNotNull(subscription);
        assertEquals(subscription.getSubscriber(), subscriber);
        assertTrue(subscription.getAuthors().isEmpty());
    }

    @Test
    public void getSubscription_crossSubscription() throws Exception {
        User first = createDefaultUser();
        User second = createDefaultUser();
        User third = createDefaultUser();

        subscriptionDao.create(first, second);
        subscriptionDao.create(second, first);
        subscriptionDao.create(second, third);

        Subscription firstSubscription = subscriptionDao.getByUser(first);

        assertNotNull(firstSubscription);
        assertEquals(firstSubscription.getSubscriber(), first);
        assertEquals(firstSubscription.getAuthors(), Collections.singletonList(second));

        Subscription secondSubscription = subscriptionDao.getByUser(second);

        assertNotNull(secondSubscription);
        assertEquals(secondSubscription.getSubscriber(), second);
        assertEquals(secondSubscription.getAuthors(), Arrays.asList(first, third));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void create_alreadyExists() throws Exception {
        User first = createDefaultUser();
        User second = createDefaultUser();

        subscriptionDao.create(first, second);
        subscriptionDao.create(first, second);
    }

    private User createDefaultUser() {
        User user = User.builder()
                .withUsername(UUID.randomUUID().toString())
                .withPassword("password")
                .build();

        return userDao.create(user);
    }

    @Configuration
    static class LocalContext {

        @Bean
        public SubscriptionDao subscriptionDao() {
            return new SubscriptionDaoImpl();
        }

        @Bean
        public UserDao userDao() {
            return new UserDaoImpl();
        }
    }
}
