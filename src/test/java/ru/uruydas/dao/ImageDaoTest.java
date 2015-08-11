package ru.uruydas.dao;

import org.jooq.exception.DataAccessException;
import org.testng.annotations.Test;
import ru.uruydas.dao.exception.NotFoundException;
import ru.uruydas.model.ads.Ads;
import ru.uruydas.model.ads.AdsCategory;
import ru.uruydas.model.comments.Comment;
import ru.uruydas.model.images.Image;
import ru.uruydas.model.news.News;
import ru.uruydas.model.news.NewsCategory;
import ru.uruydas.model.users.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class ImageDaoTest extends DaoBaseTest {

    @Test
    public void save_hp() throws Exception {
        Image image = Image.builder()
                .withId(-1L)
                .withContentType("contentType")
                .withContent("content".getBytes())
                .build();

        Image savedImage = imageDao.save(image);

        assertNotNull(savedImage.getId());
        assertNotEquals(savedImage.getId(), image.getId());
        assertEquals(savedImage.getContentType(), image.getContentType());
        assertNull(savedImage.getContent());
    }

    @Test
    public void getById_hp() throws Exception {
        Image image = Image.builder()
                .withContentType("contentType")
                .build();

        Image savedImage = imageDao.save(image);

        Image actualImage = imageDao.getById(savedImage.getId());

        assertNotNull(actualImage.getId());
        assertEquals(actualImage.getContentType(), actualImage.getContentType());
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void getById_notFound() throws Exception {
        imageDao.getById(1L);
    }

    @Test
    public void linkToNews_hp() throws Exception {
        User user = createDefaultUser();
        NewsCategory category = createDefaultNewsCategory();
        News news = createDefaultNews(user, category);

        Image savedImage = imageDao.save(
                Image.builder()
                        .withContentType("contentType")
                        .build()
        );

        imageDao.linkToNews(savedImage, news, 1);

        List<Image> images = imageDao.getByNews(news);

        assertEquals(images.size(), 1);
        assertTrue(images.contains(savedImage));
    }

    @Test
    public void linkToNews_otherImagesInDB() throws Exception {
        User user = createDefaultUser();
        NewsCategory category = createDefaultNewsCategory();
        News news = createDefaultNews(user, category);

        Image image = Image.builder()
                .withContentType("contentType")
                .build();
        Image savedImage = imageDao.save(image);
        imageDao.save(image);

        imageDao.linkToNews(savedImage, news, 1);

        List<Image> images = imageDao.getByNews(news);

        assertEquals(images.size(), 1);
        assertTrue(images.contains(savedImage));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void linkToNews_noNews() throws Exception {
        Image savedImage = imageDao.save(
                Image.builder()
                        .withContentType("contentType")
                        .build()
        );

        imageDao.linkToNews(savedImage, News.builder().withId(1L).build(), 1);
    }

    @Test
    public void getByNews_hp() throws Exception {
        User user = createDefaultUser();
        NewsCategory category = createDefaultNewsCategory();
        News news = createDefaultNews(user, category);

        Image image = Image.builder()
                .withContentType("contentType")
                .build();
        Image firstImage = imageDao.save(image);
        Image secondImage = imageDao.save(image);

        imageDao.linkToNews(firstImage, news, 2);
        imageDao.linkToNews(secondImage, news, 1);

        List<Image> images = imageDao.getByNews(news);

        assertEquals(images.size(), 2);
        assertEquals(images, Arrays.asList(secondImage, firstImage));
    }

    @Test
    public void getByNews_noImages() throws Exception {
        User user = createDefaultUser();
        NewsCategory category = createDefaultNewsCategory();
        News news = createDefaultNews(user, category);

        List<Image> images = imageDao.getByNews(news);

        assertTrue(images.isEmpty());
    }

    @Test
    public void linkToComment_hp() throws Exception {
        User user = createDefaultUser();
        NewsCategory category = createDefaultNewsCategory();
        News news = createDefaultNews(user, category);
        Comment comment = Comment.builder()
                .withBody("body")
                .withAuthor(user)
                .withNewsId(news.getId())
                .build();
        comment = commentDao.create(comment);

        Image savedImage = imageDao.save(
                Image.builder()
                        .withContentType("contentType")
                        .build()
        );

        imageDao.linkToComment(savedImage, comment, 1);

        List<Image> images = imageDao.getByComment(comment);

        assertEquals(images.size(), 1);
        assertTrue(images.contains(savedImage));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void linkToComment_noComment() throws Exception {
        Image savedImage = imageDao.save(
                Image.builder()
                        .withContentType("contentType")
                        .build()
        );

        imageDao.linkToComment(savedImage, Comment.builder().withId(1L).build(), 1);
    }

    @Test
    public void linkToComment_otherImagesInDB() throws Exception {
        User user = createDefaultUser();
        NewsCategory category = createDefaultNewsCategory();
        News news = createDefaultNews(user, category);
        Comment comment = Comment.builder()
                .withBody("body")
                .withAuthor(user)
                .withNewsId(news.getId())
                .build();
        comment = commentDao.create(comment);

        Image image = Image.builder()
                .withContentType("contentType")
                .build();
        Image savedImage = imageDao.save(image);
        imageDao.save(image);

        imageDao.linkToComment(savedImage, comment, 1);

        List<Image> images = imageDao.getByComment(comment);

        assertEquals(images.size(), 1);
        assertTrue(images.contains(savedImage));
    }

    @Test
    public void getByComment_hp() throws Exception {
        User user = createDefaultUser();
        NewsCategory category = createDefaultNewsCategory();
        News news = createDefaultNews(user, category);
        Comment comment = Comment.builder()
                .withBody("body")
                .withAuthor(user)
                .withNewsId(news.getId())
                .build();
        comment = commentDao.create(comment);

        Image image = Image.builder()
                .withContentType("contentType")
                .build();
        Image firstImage = imageDao.save(image);
        Image secondImage = imageDao.save(image);

        imageDao.linkToComment(firstImage, comment, 2);
        imageDao.linkToComment(secondImage, comment, 1);

        List<Image> images = imageDao.getByComment(comment);

        assertEquals(images.size(), 2);
        assertEquals(images, Arrays.asList(secondImage, firstImage));
    }

    @Test
    public void getByComment_noImages() throws Exception {
        User user = createDefaultUser();
        NewsCategory category = createDefaultNewsCategory();
        News news = createDefaultNews(user, category);
        Comment comment = Comment.builder()
                .withBody("body")
                .withAuthor(user)
                .withNewsId(news.getId())
                .build();
        comment = commentDao.create(comment);

        List<Image> images = imageDao.getByComment(comment);

        assertTrue(images.isEmpty());
    }

    @Test
    public void linkToUser_hp() throws Exception {
        User user = createDefaultUser();

        Image savedImage = imageDao.save(
                Image.builder()
                        .withContentType("contentType")
                        .build()
        );

        imageDao.linkToUser(savedImage, user);

        Optional<Image> userImage = imageDao.getByUser(user);

        assertEquals(userImage.get(), savedImage);
    }

    @Test
    public void linkToUser_otherImagesInDB() throws Exception {
        User user = createDefaultUser();

        Image image = Image.builder()
                .withContentType("contentType")
                .build();
        Image userImage = imageDao.save(image);
        imageDao.save(image);

        imageDao.linkToUser(userImage, user);

        Optional<Image> actualUserImage = imageDao.getByUser(user);

        assertEquals(actualUserImage.get(), userImage);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void linkToUser_noUser() throws Exception {
        Image savedImage = imageDao.save(
                Image.builder()
                        .withContentType("contentType")
                        .build()
        );

        imageDao.linkToUser(savedImage, User.builder().withId(1L).build());
    }

    @Test
    public void linkToUser_imageAlreadyLinked() throws Exception {
        User user = createDefaultUser();

        Image image = Image.builder()
                .withContentType("contentType")
                .build();
        Image savedImage = imageDao.save(image);
        imageDao.linkToUser(savedImage, user);

        Image anotherSavedImage = imageDao.save(image);
        imageDao.linkToUser(anotherSavedImage, user);

        Optional<Image> actualUserImage = imageDao.getByUser(user);

        assertEquals(actualUserImage.get(), anotherSavedImage);
    }

    @Test
    public void getByUser_noImage() throws Exception {
        User user = createDefaultUser();

        Optional<Image> image = imageDao.getByUser(user);

        assertFalse(image.isPresent());
    }

    @Test
    public void linkToAds_hp() throws Exception {
        AdsCategory adsCategory = createDefaultAdsCategory();
        User author = createDefaultUser();
        Ads ads = createDefaultAds(adsCategory, author);

        Image image = Image.builder()
                .withContentType("contentType")
                .build();

        image = imageDao.save(image);
        imageDao.linkToAds(image, ads, 0);

        List<Image> images = imageDao.getByAds(ads);

        assertFalse(images.isEmpty());
        assertEquals(images.get(0), image);
    }
}
