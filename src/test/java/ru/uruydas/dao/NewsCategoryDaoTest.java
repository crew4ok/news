package ru.uruydas.dao;

import org.jooq.exception.DataAccessException;
import org.testng.annotations.Test;
import ru.uruydas.dao.exception.NotFoundException;
import ru.uruydas.model.news.NewsCategory;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class NewsCategoryDaoTest extends DaoBaseTest {

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
}