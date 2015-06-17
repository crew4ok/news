package urujdas.dao;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import urujdas.dao.exception.NotFoundException;
import urujdas.model.Comment;
import urujdas.model.News;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

public class CommentDaoTest extends DaoBaseTest {

    private News defaultNews;

    @BeforeMethod
    protected void createDefaults() {
        this.defaultUser = createDefaultUser();
        this.defaultNewsCategory = createDefaultNewsCategory();
        this.defaultNews = createDefaultNews(defaultUser, defaultNewsCategory);
    }

    @Test
    public void getById_hp() {
        Comment comment = Comment.builder()
                .withId(-1L)
                .withBody("body")
                .withCreationDate(LocalDateTime.now())
                .withNewsId(defaultNews.getId())
                .withAuthor(defaultUser)
                .build();

        Comment createdComment = commentDao.create(comment);

        assertNotEquals(createdComment.getId(), comment.getId());

        assertEquals(createdComment.getBody(), createdComment.getBody());
        assertEquals(createdComment.getCreationDate(), createdComment.getCreationDate());
        assertEquals(createdComment.getNewsId(), createdComment.getNewsId());
        assertEquals(createdComment.getAuthor(), createdComment.getAuthor());
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void getById_notFound() throws Exception {
        commentDao.getById(-1L);
    }

    @Test
    public void getAll_hp() throws Exception {
        int totalCount = 10;

        List<Comment> expectedComments = new ArrayList<>();
        for (int i = 0; i < totalCount; i++) {
            Comment comment = Comment.builder()
                    .withBody("body" + i)
                    .withCreationDate(LocalDateTime.now())
                    .withNewsId(defaultNews.getId())
                    .withAuthor(defaultUser)
                    .build();
            expectedComments.add(commentDao.create(comment));
        }

        List<Comment> actualComments = commentDao.getAll(defaultNews);

        assertEquals(actualComments, expectedComments);
    }

    @Test
    public void getAll_empty() throws Exception {
        List<Comment> comments = commentDao.getAll(defaultNews);

        assertTrue(comments.isEmpty());
    }
}
