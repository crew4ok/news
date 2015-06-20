package urujdas.dao.impl.jooq;

import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordMapperProvider;
import org.jooq.RecordType;
import urujdas.dao.impl.jooq.mappers.CommentRecordMapper;
import urujdas.dao.impl.jooq.mappers.NewsCategoryRecordMapper;
import urujdas.dao.impl.jooq.mappers.NewsLightRecordMapper;
import urujdas.dao.impl.jooq.mappers.NewsRecordMapper;
import urujdas.dao.impl.jooq.mappers.UserRecordMapper;
import urujdas.model.Comment;
import urujdas.model.news.FeedNews;
import urujdas.model.news.News;
import urujdas.model.news.NewsCategory;
import urujdas.model.users.User;

public class JooqRecordMapperProvider implements RecordMapperProvider {

    private final UserRecordMapper userRecordMapper;
    private final NewsRecordMapper newsRecordMapper;
    private final NewsCategoryRecordMapper newsCategoryRecordMapper;
    private final CommentRecordMapper commentRecordMapper;

    public JooqRecordMapperProvider(UserRecordMapper userRecordMapper,
                                    NewsRecordMapper newsRecordMapper,
                                    NewsCategoryRecordMapper newsCategoryRecordMapper,
                                    CommentRecordMapper commentRecordMapper) {

        this.userRecordMapper = userRecordMapper;
        this.newsRecordMapper = newsRecordMapper;
        this.newsCategoryRecordMapper = newsCategoryRecordMapper;
        this.commentRecordMapper = commentRecordMapper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R extends Record, E> RecordMapper<R, E> provide(RecordType<R> recordType, Class<? extends E> type) {
        if (User.class.equals(type)) {
            return (RecordMapper<R, E>) userRecordMapper;
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

        if (FeedNews.class.equals(type)) {
            return (RecordMapper<R, E>) new NewsLightRecordMapper();
        }

        throw new RuntimeException("Mapper for type " + type.getName() + " is not found");
    }
}
