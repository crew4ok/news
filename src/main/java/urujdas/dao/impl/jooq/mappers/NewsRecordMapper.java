package urujdas.dao.impl.jooq.mappers;

import org.jooq.Record;
import org.jooq.RecordMapper;
import urujdas.model.News;
import urujdas.model.NewsCategory;
import urujdas.model.User;
import urujdas.tables.LikesTable;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.jooq.impl.DSL.count;
import static urujdas.tables.NewsTable.NEWS;

public class NewsRecordMapper implements RecordMapper<Record, News> {

    @Override
    public News map(Record record) {
        Timestamp creationDateTimestamp = record.getValue(NEWS.CREATION_DATE);

        LocalDateTime creationDate = Optional.ofNullable(creationDateTimestamp)
                .map(Timestamp::toLocalDateTime)
                .orElse(null);

        Integer likesCount = record.getValue(count(LikesTable.LIKES.ID));

        return News.builder()
                .withId(record.getValue(NEWS.ID))
                .withTitle(record.getValue(NEWS.TITLE))
                .withBody(record.getValue(NEWS.BODY))
                .withCreationDate(creationDate)
                .withLocation(record.getValue(NEWS.LOCATION))
                .withLikesCount(likesCount)
                .withAuthor(buildUser(record))
                .withCategory(buildNewsCategory(record))
                .build();
    }

    private User buildUser(Record record) {
        return record.into(User.class);
    }

    private NewsCategory buildNewsCategory(Record record) {
        return record.into(NewsCategory.class);
    }
}
