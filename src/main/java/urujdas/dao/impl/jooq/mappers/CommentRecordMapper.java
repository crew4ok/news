package urujdas.dao.impl.jooq.mappers;

import org.jooq.Record;
import org.jooq.RecordMapper;
import urujdas.model.comments.Comment;
import urujdas.model.users.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static urujdas.tables.CommentsTable.COMMENTS;

public class CommentRecordMapper implements RecordMapper<Record, Comment> {

    @Override
    public Comment map(Record record) {
        User author = record.into(User.class);

        LocalDateTime creationDate = Optional.ofNullable(record.getValue(COMMENTS.CREATION_DATE))
                .map(Timestamp::toLocalDateTime)
                .orElse(null);

        return Comment.builder()
                .withId(record.getValue(COMMENTS.ID))
                .withBody(record.getValue(COMMENTS.BODY))
                .withCreationDate(creationDate)
                .withNewsId(record.getValue(COMMENTS.NEWS_ID))
                .withAuthor(author)
                .build();
    }
}
