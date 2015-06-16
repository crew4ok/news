package urujdas.dao.impl.jooq.mappers;

import org.jooq.Record;
import org.jooq.RecordMapper;
import urujdas.model.Comment;
import urujdas.model.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static urujdas.tables.CommentsTable.COMMENTS;

public class CommentRecordMapper implements RecordMapper<Record, Comment> {

    private final UserRecordMapper userRecordMapper;

    public CommentRecordMapper(UserRecordMapper userRecordMapper) {
        this.userRecordMapper = userRecordMapper;
    }

    @Override
    public Comment map(Record record) {
        User author = userRecordMapper.map(record);

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
