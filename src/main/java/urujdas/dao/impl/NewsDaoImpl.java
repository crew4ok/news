package urujdas.dao.impl;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.SelectOnConditionStep;
import org.jooq.SortField;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urujdas.dao.NewsDao;
import urujdas.dao.exception.NotFoundException;
import urujdas.model.News;
import urujdas.model.NewsLight;
import urujdas.model.Subscription;
import urujdas.model.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.jooq.impl.DSL.coalesce;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.exists;
import static org.jooq.impl.DSL.when;
import static urujdas.tables.CommentsTable.COMMENTS;
import static urujdas.tables.FavouritesTable.FAVOURITES;
import static urujdas.tables.LikesTable.LIKES;
import static urujdas.tables.NewsCategoriesTable.NEWS_CATEGORIES;
import static urujdas.tables.NewsTable.NEWS;
import static urujdas.tables.UsersTable.USERS;

@Repository
public class NewsDaoImpl implements NewsDao {

    @Autowired
    private DSLContext ctx;

    @Override
    public News getById(Long id) {
        List<News> result = select(
                singletonList(NEWS.ID.equal(id)),
                emptyMap(),
                Optional.empty()
        );
        if (!result.isEmpty()) {
            return result.get(0);
        }
        throw new NotFoundException(News.class, id);
    }

    @Override
    public List<NewsLight> getLatestAllLight(User currentUser, int latestCount) {
        return selectLightNews(currentUser.getId(), Optional.of(latestCount));
    }

    private List<News> select(Collection<Condition> conditions,
                              Map<Table<?>, Condition> joins,
                              Optional<Integer> limit) {

        List<Field<?>> fields = getNewsFields();

        SelectOnConditionStep<Record> step = ctx.select(fields)
                .from(NEWS)
                .join(USERS).on(NEWS.AUTHOR.equal(USERS.ID))
                .join(NEWS_CATEGORIES).on(NEWS.CATEGORY_ID.equal(NEWS_CATEGORIES.ID))
                .leftOuterJoin(LIKES).on(NEWS.ID.equal(LIKES.NEWS_ID));

        joins.forEach((t, c) -> step.join(t).on(c));

        step.where(conditions)
                .groupBy(groupByClause())
                .orderBy(orderByIdClause());

        if (limit.isPresent()) {
            step.limit(limit.get());
        }
        return step.fetch()
                .into(News.class);
    }

    private List<NewsLight> selectLightNews(Long currentUserId, Optional<Integer> limit) {
        Field<Object> commentsCount = ctx.select(count(COMMENTS.ID))
                .from(COMMENTS)
                .where(COMMENTS.NEWS_ID.equal(NEWS.ID))
                .groupBy(COMMENTS.NEWS_ID)
                .asField();

        Field<Object> likesCount = ctx.select(count(LIKES.ID))
                .from(LIKES)
                .where(LIKES.NEWS_ID.equal(NEWS.ID))
                .groupBy(LIKES.NEWS_ID)
                .asField();

        Select<?> favoured = ctx.selectOne()
                .from(FAVOURITES)
                .where(FAVOURITES.USER_ID.equal(currentUserId))
                .and(FAVOURITES.NEWS_ID.equal(NEWS.ID));

        Select<?> liked = ctx.selectOne()
                .from(LIKES)
                .where(LIKES.LIKER.equal(currentUserId))
                .and(LIKES.NEWS_ID.equal(NEWS.ID));

        List<Field<?>> fields = Arrays.asList(
                NEWS.ID, NEWS.TITLE, NEWS.BODY,
                USERS.USERNAME, USERS.FIRSTNAME, USERS.LASTNAME,
                coalesce(commentsCount, 0).as("comments_count"),
                coalesce(likesCount, 0).as("likes_count"),
                when(exists(favoured), "true").otherwise("false").as("user_favoured"),
                when(exists(liked), "true").otherwise("false").as("user_liked")
        );

        return ctx.select(fields)
                .from(NEWS)
                .join(USERS).on(NEWS.AUTHOR.equal(USERS.ID))
                .orderBy(NEWS.ID.desc())
                .limit(limit.get())
                .fetch()
                .into(NewsLight.class);
    }

    private List<Field<?>> getNewsFields() {
        List<Field<?>> fields = new ArrayList<>(asList(NEWS.fields()));
        // all user fields excluding password
        fields.addAll(asList(USERS.fields()));
        fields.remove(USERS.PASSWORD);
        fields.addAll(asList(NEWS_CATEGORIES.fields()));
        fields.add(count(LIKES.ID));
        return fields;
    }

    @Override
    public List<News> getLatestAll(int latestCount) {
        return select(
                emptyList(),
                emptyMap(),
                Optional.of(latestCount)
        );
    }

    @Override
    public List<News> getAllFromId(long id, int count) {
        return select(
                singletonList(pagingWhereClause(id)),
                emptyMap(),
                Optional.of(count)
        );
    }

    @Override
    public List<News> getLatestByUser(User user, int count) {
        return select(
                singletonList(authorWhereClause(user)),
                emptyMap(),
                Optional.of(count)
        );
    }

    @Override
    public List<News> getByUserFromId(User user, Long id, int count) {
        return select(
                asList(pagingWhereClause(id), authorWhereClause(user)),
                emptyMap(),
                Optional.of(count)
        );
    }

    @Override
    public List<News> getLatestBySubscription(Subscription subscription, int count) {
        List<Long> authorsIds = subscription.getAuthors().stream()
                .map(User::getId)
                .collect(Collectors.toList());

        return select(
                singletonList(NEWS.AUTHOR.in(authorsIds)),
                emptyMap(),
                Optional.of(count)
        );
    }

    @Override
    public List<News> getBySubscriptionFromId(Subscription subscription, Long id, int count) {
        List<Long> authorsIds = subscription.getAuthors().stream()
                .map(User::getId)
                .collect(Collectors.toList());

        return select(
                asList(NEWS.AUTHOR.in(authorsIds), pagingWhereClause(id)),
                emptyMap(),
                Optional.of(count)
        );
    }

    @Override
    public List<News> getLatestFavourites(User user, int count) {
        return select(
                singletonList(FAVOURITES.USER_ID.equal(user.getId())),
                singletonMap(FAVOURITES, FAVOURITES.NEWS_ID.equal(NEWS.ID)),
                Optional.of(count)
        );
    }

    @Override
    public List<News> getFavouritesFromId(User user, Long id, int count) {
        return select(
                asList(FAVOURITES.USER_ID.equal(user.getId()), pagingWhereClause(id)),
                singletonMap(FAVOURITES, FAVOURITES.NEWS_ID.equal(NEWS.ID)),
                Optional.of(count)
        );
    }

    private Collection<Field<?>> groupByClause() {
        return asList(NEWS.ID, USERS.ID, NEWS_CATEGORIES.ID);
    }

    private Condition authorWhereClause(User user) {
        return NEWS.AUTHOR.equal(user.getId());
    }

    private Condition pagingWhereClause(Long id) {
        return NEWS.ID.le(id);
    }

    private SortField<?> orderByIdClause() {
        return NEWS.ID.desc();
    }

    @Override
    public News create(News news) {
        Long id = ctx.insertInto(NEWS)
                .set(NEWS.TITLE, news.getTitle())
                .set(NEWS.BODY, news.getBody())
                .set(NEWS.CREATION_DATE, Timestamp.valueOf(news.getCreationDate()))
                .set(NEWS.LOCATION, news.getLocation())
                .set(NEWS.AUTHOR, news.getAuthor().getId())
                .set(NEWS.CATEGORY_ID, news.getCategory().getId())
                .returning(NEWS.ID)
                .fetchOne()
                .getId();

        return getById(id);
    }

    @Override
    public void addToFavourites(User user, News news) {
        ctx.insertInto(FAVOURITES)
                .set(FAVOURITES.NEWS_ID, news.getId())
                .set(FAVOURITES.USER_ID, user.getId())
                .execute();
    }

    @Override
    public void like(User currentUser, News news) {
        ctx.insertInto(LIKES)
                .set(LIKES.LIKER, currentUser.getId())
                .set(LIKES.NEWS_ID, news.getId())
                .execute();
    }

    @Override
    public void dislike(User currentUser, News news) {
        ctx.deleteFrom(LIKES)
                .where(LIKES.LIKER.equal(currentUser.getId()))
                .and(LIKES.NEWS_ID.equal(news.getId()))
                .execute();
    }

    @Override
    public List<User> getLikers(News news) {
        ArrayList<Field<?>> fields = new ArrayList<>(Arrays.asList(USERS.fields()));
        fields.remove(USERS.PASSWORD);

        return ctx.select(fields)
                .from(LIKES)
                .join(USERS).on(LIKES.LIKER.equal(USERS.ID))
                .where(LIKES.NEWS_ID.equal(news.getId()))
                .fetch()
                .into(User.class);
    }
}
