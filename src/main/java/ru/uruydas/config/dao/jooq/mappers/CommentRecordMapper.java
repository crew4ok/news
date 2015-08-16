package ru.uruydas.config.dao.jooq.mappers;

import org.jooq.Record;
import org.jooq.RecordMapper;
import ru.uruydas.comments.model.Comment;
import ru.uruydas.users.model.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static ru.uruydas.common.util.MapperUtils.fromNullable;
import static ru.uruydas.tables.CommentsTable.COMMENTS;

public class CommentRecordMapper implements RecordMapper<Record, Comment> {

    @Override
    public Comment map(Record record) {
        User author = record.into(User.class);

        LocalDateTime creationDate = fromNullable(record.getValue(COMMENTS.CREATION_DATE), Timestamp::toLocalDateTime);

        return Comment.builder()
                .withId(record.getValue(COMMENTS.ID))
                .withBody(record.getValue(COMMENTS.BODY))
                .withCreationDate(creationDate)
                .withNewsId(record.getValue(COMMENTS.NEWS_ID))
                .withAuthor(author)
                .build();
    }
}
