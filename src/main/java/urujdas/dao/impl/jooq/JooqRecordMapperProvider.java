package urujdas.dao.impl.jooq;

import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordMapperProvider;
import org.jooq.RecordType;
import urujdas.dao.impl.jooq.mappers.CommentRecordMapper;
import urujdas.dao.impl.jooq.mappers.NewsCategoryRecordMapper;
import urujdas.dao.impl.jooq.mappers.NewsRecordMapper;
import urujdas.dao.impl.jooq.mappers.UserFilterRecordMapper;
import urujdas.dao.impl.jooq.mappers.UserRecordMapper;
import urujdas.model.comments.Comment;
import urujdas.model.news.News;
import urujdas.model.news.NewsCategory;
import urujdas.model.users.User;
import urujdas.model.users.UserFilter;

public class JooqRecordMapperProvider implements RecordMapperProvider {

    private final UserRecordMapper userRecordMapper;
    private final UserFilterRecordMapper userFilterRecordMapper;
    private final NewsRecordMapper newsRecordMapper;
    private final NewsCategoryRecordMapper newsCategoryRecordMapper;
    private final CommentRecordMapper commentRecordMapper;

    public JooqRecordMapperProvider(UserRecordMapper userRecordMapper,
                                    UserFilterRecordMapper userFilterRecordMapper,
                                    NewsRecordMapper newsRecordMapper,
                                    NewsCategoryRecordMapper newsCategoryRecordMapper,
                                    CommentRecordMapper commentRecordMapper) {

        this.userRecordMapper = userRecordMapper;
        this.userFilterRecordMapper = userFilterRecordMapper;
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

        throw new RuntimeException("Mapper for type " + type.getName() + " is not found");
    }
}
