package ru.uruydas.dao;

import org.testng.annotations.Test;
import ru.uruydas.ads.model.AdsCategory;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class AdsCategoryDaoTest extends DaoBaseTest {

    @Test
    public void create_hp() throws Exception {
        String categoryName = "test_category";
        AdsCategory category = this.adsCategoryDao.create(new AdsCategory(categoryName));

        String subCategoryName = "test subcategory";
        AdsCategory subCategory = this.adsCategoryDao.create(new AdsCategory(subCategoryName, category.getId()));

        assertEquals(category.getName(), categoryName);

        assertEquals(subCategory.getName(), subCategoryName);
        assertEquals(subCategory.getParentCategoryId(), category.getId());
    }

    @Test
    public void getById_hp() throws Exception {
        String categoryName = "test_category";
        AdsCategory category = this.adsCategoryDao.create(new AdsCategory(categoryName));

        String subCategoryName = "test subcategory";
        AdsCategory subCategory = this.adsCategoryDao.create(new AdsCategory(subCategoryName, category.getId()));

        subCategory = this.adsCategoryDao.getById(subCategory.getId());

        assertEquals(subCategory.getName(), subCategoryName);
        assertEquals(subCategory.getParentCategoryId(), category.getId());
    }

    @Test
    public void getAllCategories_hp() throws Exception {
        String categoryName = "test_category";
        AdsCategory category = this.adsCategoryDao.create(new AdsCategory(categoryName));

        String subCategoryName = "test subcategory";
        AdsCategory subCategory = this.adsCategoryDao.create(new AdsCategory(subCategoryName, category.getId()));

        List<AdsCategory> categories = this.adsCategoryDao.getAllCategories();

        assertEquals(categories.size(), 1);
        assertEquals(categories.get(0), category);
    }

    @Test
    public void getAllSubCategories_hp() throws Exception {
        String categoryName = "test_category";
        AdsCategory category = this.adsCategoryDao.create(new AdsCategory(categoryName));

        String subCategoryName = "test subcategory";
        AdsCategory subCategory = this.adsCategoryDao.create(new AdsCategory(subCategoryName, category.getId()));

        List<AdsCategory> categories = this.adsCategoryDao.getAllSubCategories(category);

        assertEquals(categories.size(), 1);
        assertEquals(categories.get(0), subCategory);
    }
}
