package urujdas.dao.impl.jooq;

import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordMapperProvider;
import org.jooq.RecordType;
import urujdas.dao.impl.jooq.mappers.NewsCategoryRecordMapper;
import urujdas.dao.impl.jooq.mappers.NewsRecordMapper;
import urujdas.dao.impl.jooq.mappers.UserRecordMapper;
import urujdas.model.News;
import urujdas.model.NewsCategory;
import urujdas.model.User;

public class JooqRecordMapperProvider implements RecordMapperProvider {

    private final UserRecordMapper userRecordMapper;
    private final NewsRecordMapper newsRecordMapper;
    private final NewsCategoryRecordMapper newsCategoryRecordMapper;

    public JooqRecordMapperProvider(UserRecordMapper userRecordMapper,
                                    NewsRecordMapper newsRecordMapper, NewsCategoryRecordMapper newsCategoryRecordMapper) {

        this.userRecordMapper = userRecordMapper;
        this.newsRecordMapper = newsRecordMapper;
        this.newsCategoryRecordMapper = newsCategoryRecordMapper;
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

        throw new RuntimeException("Mapper for type " + type.getName() + " is not found");
    }
}
