package urujdas.dao.impl;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urujdas.dao.CommentDao;
import urujdas.dao.exception.NotFoundException;
import urujdas.model.Comment;
import urujdas.model.News;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static urujdas.tables.CommentsTable.COMMENTS;
import static urujdas.tables.UsersTable.USERS;

@Repository
public class CommentDaoImpl implements CommentDao {

    @Autowired
    private DSLContext ctx;

    @Override
    public List<Comment> getAll(News news) {
        return select(singletonList(COMMENTS.NEWS_ID.equal(news.getId())));
    }

    @Override
    public Comment getById(Long id) {
        List<Comment> comments = select(singletonList(COMMENTS.ID.equal(id)));
        if (!comments.isEmpty()) {
            return comments.get(0);
        }

        throw new NotFoundException(Comment.class, id);
    }

    @Override
    public Comment create(Comment comment) {
        Long id = ctx.insertInto(COMMENTS)
                .set(COMMENTS.BODY, comment.getBody())
                .set(COMMENTS.CREATION_DATE, Timestamp.valueOf(comment.getCreationDate()))
                .set(COMMENTS.NEWS_ID, comment.getNewsId())
                .set(COMMENTS.AUTHOR, comment.getAuthor().getId())
                .returning(COMMENTS.ID)
                .fetchOne()
                .getId();

        return getById(id);
    }

    private List<Comment> select(List<Condition> conditions) {
        return ctx.select(getCommentFields())
                .from(COMMENTS)
                .join(USERS).on(COMMENTS.AUTHOR.equal(USERS.ID))
                .where(conditions)
                .orderBy(COMMENTS.ID.asc())
                .fetch()
                .into(Comment.class);
    }

    private List<Field<?>> getCommentFields() {
        Field<?>[] commentFields = COMMENTS.fields();
        Field<?>[] userFields = USERS.fields();

        List<Field<?>> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(commentFields));
        fields.addAll(Arrays.asList(userFields));

        return fields;
    }
}
