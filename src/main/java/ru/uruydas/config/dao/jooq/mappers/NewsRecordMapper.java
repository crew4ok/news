package ru.uruydas.config.dao.jooq.mappers;

import org.jooq.Record;
import org.jooq.RecordMapper;
import ru.uruydas.news.model.News;
import ru.uruydas.news.model.NewsCategory;
import ru.uruydas.users.model.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static ru.uruydas.common.util.MapperUtils.fromNullable;
import static ru.uruydas.tables.NewsTable.NEWS;

public class NewsRecordMapper implements RecordMapper<Record, News> {

    @Override
    public News map(Record record) {
        LocalDateTime creationDate = fromNullable(record.getValue(NEWS.CREATION_DATE), Timestamp::toLocalDateTime);

        return News.builder()
                .withId(record.getValue(NEWS.ID))
                .withTitle(record.getValue(NEWS.TITLE))
                .withBody(record.getValue(NEWS.BODY))
                .withCreationDate(creationDate)
                .withLocation(record.getValue(NEWS.LOCATION))
                .withAuthor(buildUser(record))
                .withCategory(buildNewsCategory(record))
                .withCommentsCount(record.getValue("comments_count", Integer.class))
                .withLikesCount(record.getValue("likes_count", Integer.class))
                .withCurrentUserFavoured(record.getValue("user_favoured", Boolean.class))
                .withCurrentUserLiked(record.getValue("user_liked", Boolean.class))
                .build();
    }

    private User buildUser(Record record) {
        return record.into(User.class);
    }

    private NewsCategory buildNewsCategory(Record record) {
        return record.into(NewsCategory.class);
    }
}
