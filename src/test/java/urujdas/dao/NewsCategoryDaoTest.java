package urujdas.dao;

import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;
import urujdas.config.DaoConfig;
import urujdas.dao.exception.NotFoundException;
import urujdas.dao.impl.NewsCategoryDaoImpl;
import urujdas.model.NewsCategory;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@ContextConfiguration(classes = {
        DaoConfig.class,
        NewsCategoryDaoTest.LocalContext.class
})
public class NewsCategoryDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private NewsCategoryDao newsCategoryDao;

    @Test
    public void create_hp() throws Exception {
        NewsCategory category = new NewsCategory("category");

        NewsCategory createdCategory = newsCategoryDao.create(category);

        assertNotNull(createdCategory.getId());
        assertEquals(createdCategory.getName(), category.getName());
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void create_notUniqueName() throws Exception {
        NewsCategory category = new NewsCategory("category");

        newsCategoryDao.create(category);
        newsCategoryDao.create(category);
    }

    @Test
    public void getAll_hp() throws Exception {
        int totalCount = 10;
        List<NewsCategory> expectedCategories = new ArrayList<>(totalCount);
        for (int i = 0; i < totalCount; i++) {
            NewsCategory newsCategory = new NewsCategory("category" + i);
            expectedCategories.add(newsCategory);
            newsCategoryDao.create(newsCategory);
        }

        List<NewsCategory> actualCategories = newsCategoryDao.getAll();

        assertEquals(actualCategories.size(), expectedCategories.size());
        expectedCategories.forEach(expectedCategory ->
                assertTrue(
                        actualCategories.stream()
                                .anyMatch(c -> c.getName().equals(expectedCategory.getName()))
                )
        );
    }

    @Test
    public void getAll_empty() throws Exception {
        List<NewsCategory> categories = newsCategoryDao.getAll();
        assertTrue(categories.isEmpty());
    }

    @Test
    public void getById_hp() throws Exception {
        NewsCategory category = new NewsCategory("category");

        NewsCategory createdCategory = newsCategoryDao.create(category);

        assertNotNull(createdCategory.getId());

        NewsCategory actualCategory = newsCategoryDao.getById(createdCategory.getId());

        assertEquals(actualCategory, createdCategory);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void getById_notFound() throws Exception {
        newsCategoryDao.getById(-1L);
    }

    @Configuration
    static class LocalContext {

        @Bean
        public NewsCategoryDao newsCategoryDao() {
            return new NewsCategoryDaoImpl();
        }

    }
}