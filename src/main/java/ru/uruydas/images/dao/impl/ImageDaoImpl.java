package ru.uruydas.images.dao.impl;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.uruydas.ads.model.Ads;
import ru.uruydas.comments.model.Comment;
import ru.uruydas.common.dao.exception.NotFoundException;
import ru.uruydas.images.dao.ImageDao;
import ru.uruydas.images.model.Image;
import ru.uruydas.news.model.News;
import ru.uruydas.tables.records.ImagesRecord;
import ru.uruydas.users.model.User;

import java.util.List;
import java.util.Optional;

import static ru.uruydas.tables.ImagesTable.IMAGES;

@Repository
public class ImageDaoImpl implements ImageDao {

    @Autowired
    private DSLContext ctx;

    @Override
    public Image getById(Long imageId) {
        ImagesRecord imageRecord = ctx.selectFrom(IMAGES)
                .where(IMAGES.ID.equal(imageId))
                .fetchOne();

        if (imageRecord != null) {
            return imageRecord.into(Image.class);
        }

        throw new NotFoundException(Image.class, imageId);
    }

    @Override
    public Image save(Image image) {
        return ctx.insertInto(IMAGES)
                .set(IMAGES.CONTENT_TYPE, image.getContentType())
                .returning(IMAGES.fields())
                .fetchOne()
                .into(Image.class);
    }

    @Override
    public void linkToNews(Image image, News news, int ordering) {
        ctx.update(IMAGES)
                .set(IMAGES.NEWS_ID, news.getId())
                .set(IMAGES.ORDERING, ordering)
                .where(IMAGES.ID.equal(image.getId()))
                .execute();
    }

    @Override
    public List<Image> getByNews(News news) {
        return ctx.selectFrom(IMAGES)
                .where(IMAGES.NEWS_ID.equal(news.getId()))
                .orderBy(IMAGES.ORDERING.asc())
                .fetch()
                .into(Image.class);
    }

    @Override
    public void linkToComment(Image image, Comment comment, int ordering) {
        ctx.update(IMAGES)
                .set(IMAGES.COMMENT_ID, comment.getId())
                .set(IMAGES.ORDERING, ordering)
                .where(IMAGES.ID.equal(image.getId()))
                .execute();
    }

    @Override
    public List<Image> getByComment(Comment comment) {
        return ctx.selectFrom(IMAGES)
                .where(IMAGES.COMMENT_ID.equal(comment.getId()))
                .orderBy(IMAGES.ORDERING.asc())
                .fetch()
                .into(Image.class);
    }

    @Override
    public void linkToUser(Image image, User user) {
        Optional<Image> currentImage = getByUser(user);
        if (currentImage.isPresent()) {
            ctx.update(IMAGES)
                    .set(IMAGES.USER_ID, (Long)null)
                    .where(IMAGES.ID.equal(currentImage.get().getId()))
                    .execute();
        }

        ctx.update(IMAGES)
                .set(IMAGES.USER_ID, user.getId())
                .where(IMAGES.ID.equal(image.getId()))
                .execute();
    }

    @Override
    public Optional<Image> getByUser(User user) {
        ImagesRecord imageRecord = ctx.selectFrom(IMAGES)
                .where(IMAGES.USER_ID.equal(user.getId()))
                .fetchOne();

        return Optional.ofNullable(imageRecord)
                .map(r -> r.into(Image.class));
    }

    @Override
    public void linkToAds(Image image, Ads ads, int ordering) {
        ctx.update(IMAGES)
                .set(IMAGES.ADS_ID, ads.getId())
                .set(IMAGES.ORDERING, ordering)
                .where(IMAGES.ID.equal(image.getId()))
                .execute();
    }

    @Override
    public List<Image> getByAds(Ads ads) {
        return ctx.selectFrom(IMAGES)
                .where(IMAGES.ADS_ID.equal(ads.getId()))
                .orderBy(IMAGES.ORDERING.asc())
                .fetch()
                .into(Image.class);
    }
}
