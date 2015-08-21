package ru.uruydas.config.dao.jooq;

import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordMapperProvider;
import org.jooq.RecordType;
import ru.uruydas.ads.model.Ads;
import ru.uruydas.ads.model.AdsCategory;
import ru.uruydas.ads.model.AdsType;
import ru.uruydas.comments.model.Comment;
import ru.uruydas.config.dao.jooq.mappers.AdsCategoryRecordMapper;
import ru.uruydas.config.dao.jooq.mappers.AdsRecordMapper;
import ru.uruydas.config.dao.jooq.mappers.AdsTypeRecordMapper;
import ru.uruydas.config.dao.jooq.mappers.CommentRecordMapper;
import ru.uruydas.config.dao.jooq.mappers.ImageRecordMapper;
import ru.uruydas.config.dao.jooq.mappers.NewsCategoryRecordMapper;
import ru.uruydas.config.dao.jooq.mappers.NewsRecordMapper;
import ru.uruydas.config.dao.jooq.mappers.PersistentUserSessionRecordMapper;
import ru.uruydas.config.dao.jooq.mappers.UserFilterRecordMapper;
import ru.uruydas.config.dao.jooq.mappers.UserRecordMapper;
import ru.uruydas.images.model.Image;
import ru.uruydas.news.model.News;
import ru.uruydas.news.model.NewsCategory;
import ru.uruydas.rememberme.model.PersistentUserSession;
import ru.uruydas.users.model.User;
import ru.uruydas.users.model.UserFilter;

public class JooqRecordMapperProvider implements RecordMapperProvider {

    private final UserRecordMapper userRecordMapper;
    private final UserFilterRecordMapper userFilterRecordMapper;
    private final NewsRecordMapper newsRecordMapper;
    private final NewsCategoryRecordMapper newsCategoryRecordMapper;
    private final CommentRecordMapper commentRecordMapper;
    private final ImageRecordMapper imageRecordMapper;
    private final AdsRecordMapper adsRecordMapper;
    private final AdsCategoryRecordMapper adsCategoryRecordMapper;
    private final AdsTypeRecordMapper adsTypeRecordMapper;
    private final PersistentUserSessionRecordMapper persistentUserSessionRecordMapper;

    public JooqRecordMapperProvider(UserRecordMapper userRecordMapper,
                                    UserFilterRecordMapper userFilterRecordMapper,
                                    NewsRecordMapper newsRecordMapper,
                                    NewsCategoryRecordMapper newsCategoryRecordMapper,
                                    CommentRecordMapper commentRecordMapper,
                                    ImageRecordMapper imageRecordMapper,
                                    AdsRecordMapper adsRecordMapper,
                                    AdsCategoryRecordMapper adsCategoryRecordMapper,
                                    AdsTypeRecordMapper adsTypeRecordMapper,
                                    PersistentUserSessionRecordMapper persistentUserSessionRecordMapper) {

        this.userRecordMapper = userRecordMapper;
        this.userFilterRecordMapper = userFilterRecordMapper;
        this.newsRecordMapper = newsRecordMapper;
        this.newsCategoryRecordMapper = newsCategoryRecordMapper;
        this.commentRecordMapper = commentRecordMapper;
        this.imageRecordMapper = imageRecordMapper;
        this.adsRecordMapper = adsRecordMapper;
        this.adsCategoryRecordMapper = adsCategoryRecordMapper;
        this.adsTypeRecordMapper = adsTypeRecordMapper;
        this.persistentUserSessionRecordMapper = persistentUserSessionRecordMapper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R extends Record, E> RecordMapper<R, E> provide(RecordType<R> recordType, Class<? extends E> type) {
        if (User.class.equals(type)) {
            return (RecordMapper<R, E>) userRecordMapper;
        }

        if (UserFilter.class.equals(type)) {
            return (RecordMapper<R, E>) userFilterRecordMapper;
        }

        if (News.class.equals(type)) {
            return (RecordMapper<R, E>) newsRecordMapper;
        }

        if (NewsCategory.class.equals(type)) {
            return (RecordMapper<R, E>) newsCategoryRecordMapper;
        }

        if (Comment.class.equals(type)) {
            return (RecordMapper<R, E>) commentRecordMapper;
        }

        if (Image.class.equals(type)) {
            return (RecordMapper<R, E>) imageRecordMapper;
        }

        if (Ads.class.equals(type)) {
            return (RecordMapper<R, E>) adsRecordMapper;
        }

        if (AdsCategory.class.equals(type)) {
            return (RecordMapper<R, E>) adsCategoryRecordMapper;
        }

        if (AdsType.class.equals(type)) {
            return (RecordMapper<R, E>) adsTypeRecordMapper;
        }

        if (PersistentUserSession.class.equals(type)) {
            return (RecordMapper<R, E>) persistentUserSessionRecordMapper;
        }

        throw new RuntimeException("Mapper for type " + type.getName() + " is not found");
    }
}
