package ru.uruydas.dao;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.uruydas.ads.model.AdsCategory;
import ru.uruydas.ads.model.AdsType;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class AdsTypeDaoTest extends DaoBaseTest {

    @BeforeMethod
    public void setupDefaults() {
        this.defaultUser = createDefaultUser();
    }

    @Test
    public void create_hp() throws Exception {
        AdsCategory adsCategory = createDefaultAdsCategory();

        AdsType adsType = new AdsType("adsType", adsCategory.getId());

        AdsType actualAdsType = adsTypeDao.create(adsType);

        assertEquals(actualAdsType.getName(), adsType.getName());
        assertEquals(actualAdsType.getAdsCategoryId(), adsType.getAdsCategoryId());
    }

    @Test
    public void getById_hp() throws Exception {
        AdsCategory adsCategory = createDefaultAdsCategory();

        AdsType adsType = new AdsType("adsType", adsCategory.getId());
        adsType = adsTypeDao.create(adsType);

        AdsType actualAdsType = adsTypeDao.getById(adsType.getId());

        assertEquals(actualAdsType.getName(), adsType.getName());
        assertEquals(actualAdsType.getAdsCategoryId(), adsType.getAdsCategoryId());
    }

    @Test
    public void getByCategory_hp() throws Exception {
        AdsCategory adsCategory = createDefaultAdsCategory();

        AdsType adsType = new AdsType("adsType", adsCategory.getId());
        adsType = adsTypeDao.create(adsType);

        List<AdsType> actualAdsTypes = adsTypeDao.getByCategory(adsCategory);

        assertEquals(actualAdsTypes.size(), 1);

        AdsType actualAdsType = actualAdsTypes.get(0);

        assertEquals(actualAdsType.getName(), adsType.getName());
        assertEquals(actualAdsType.getAdsCategoryId(), adsType.getAdsCategoryId());
    }
}