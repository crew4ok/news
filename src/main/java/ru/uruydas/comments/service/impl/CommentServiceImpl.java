package ru.uruydas.comments.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.uruydas.comments.dao.CommentDao;
import ru.uruydas.comments.model.Comment;
import ru.uruydas.comments.service.CommentService;
import ru.uruydas.common.util.Validation;
import ru.uruydas.images.dao.ImageDao;
import ru.uruydas.images.model.Image;
import ru.uruydas.news.dao.NewsDao;
import ru.uruydas.news.model.News;
import ru.uruydas.users.model.User;
import ru.uruydas.users.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private ImageDao imageDao;

    @Override
    public List<Comment> getAll(Long newsId) {
        Validation.isGreaterThanZero(newsId);

        News news = newsDao.getById(userService.getCurrentUser(), newsId);

        return constructComments(commentDao.getAll(news));
    }

    @Override
    public void create(Comment comment) {
        User currentUser = userService.getCurrentUser();
        News news = newsDao.getById(currentUser, comment.getNewsId());

        Comment commentToCreate = Comment.fromComment(comment)
                .withAuthor(currentUser)
                .build();

        Comment createdComment = commentDao.create(commentToCreate);

        if (comment.getImageIds() != null) {
            for (int i = 0; i < comment.getImageIds().size(); i++) {
                Long imageId = comment.getImageIds().get(i);
                Image image = imageDao.getById(imageId);

                imageDao.linkToComment(image, createdComment, i);
            }
        }
    }

    private List<Comment> constructComments(List<Comment> comments) {
        return comments.stream()
                .map(c -> {
                    List<Image> images = imageDao.getByComment(c);
                    List<Long> imageIds = images.stream()
                            .map(Image::getId)
                            .collect(Collectors.toList());

                    User author = c.getAuthor();
                    Optional<Image> userImage = imageDao.getByUser(author);
                    if (userImage.isPresent()) {
                        author = User.fromUser(author)
                                .withImageId(userImage.get().getId())
                                .build();
                    }

                    return Comment.fromComment(c)
                            .withImageIds(imageIds)
                            .withAuthor(author)
                            .build();
                }).collect(Collectors.toList());
    }
}
