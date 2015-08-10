package ru.uruydas.dao.impl.jooq.mappers;

import org.jooq.Record;
import org.jooq.RecordMapper;
import ru.uruydas.model.comments.Comment;
import ru.uruydas.model.users.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static ru.uruydas.tables.CommentsTable.COMMENTS;
import static ru.uruydas.util.MapperUtils.fromNullable;

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
