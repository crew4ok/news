package ru.uruydas.dao;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.uruydas.common.dao.exception.NotFoundException;
import ru.uruydas.model.ads.Ads;
import ru.uruydas.model.ads.AdsType;

import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

public class AdsDaoTest extends DaoBaseTest {

    @BeforeMethod
    public void setupDefaults() {
        this.defaultUser = createDefaultUser();
    }

    @Test
    public void create_hp() throws Exception {
        Ads ads = Ads.builder()
                .withId(-1L)
                .withTitle("title")
                .withDescription("description")
                .withAdsType(AdsType.BUY)
                .withCreationDate(LocalDateTime.now().minusDays(1))
                .withPhone("phone")
                .withEmail("email")
                .withCity("city")
                .withPrice(100L)
                .withAuthor(defaultUser)
                .withAdsCategory(createDefaultAdsCategory())
                .build();

        Ads createdAds = adsDao.create(ads);

        assertNotNull(createdAds);
        assertNotEquals(createdAds.getId(), ads.getId());
        assertEquals(createdAds.getTitle(), ads.getTitle());
        assertEquals(createdAds.getDescription(), ads.getDescription());
        assertEquals(createdAds.getAdsType(), ads.getAdsType());
        assertNotEquals(createdAds.getCreationDate(), ads.getCreationDate());
        assertEquals(createdAds.getPhone(), ads.getPhone());
        assertEquals(createdAds.getEmail(), ads.getEmail());
        assertEquals(createdAds.getCity(), ads.getCity());
        assertEquals(createdAds.getPrice(), ads.getPrice());
        assertEquals(createdAds.getAuthor(), ads.getAuthor());
    }

    @Test
    public void getById_hp() throws Exception {
        Ads ads = Ads.builder()
                .withTitle("title")
                .withDescription("description")
                .withAdsType(AdsType.BUY)
                .withPhone("phone")
                .withEmail("email")
                .withCity("city")
                .withPrice(100L)
                .withAuthor(defaultUser)
                .withAdsCategory(createDefaultAdsCategory())
                .build();

        Ads createdAds = adsDao.create(ads);

        Ads actualAds = adsDao.getById(createdAds.getId());

        assertEquals(actualAds, createdAds);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void getById_notFound() throws Exception {
        adsDao.getById(-1L);
    }
}