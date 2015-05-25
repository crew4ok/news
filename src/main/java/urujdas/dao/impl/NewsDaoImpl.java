package urujdas.dao.impl;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectOnConditionStep;
import org.jooq.SortField;
import org.jooq.TableLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urujdas.dao.NewsDao;
import urujdas.model.News;
import urujdas.model.Subscription;
import urujdas.model.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.count;
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
        Record record = selectClause()
                .where(NEWS.ID.equal(id))
                .groupBy(groupByClause())
                .fetchOne();
        if (record != null) {
            return record.into(News.class);
        } else {
            return null;
        }
    }

    private List<News> select(Collection<Condition> conditions,
                              Collection<TableLike<?>> joins) {

        List<Field<?>> fields = new ArrayList<>(Arrays.asList(NEWS.fields()));
        fields.addAll(Arrays.asList(USERS.fields()));
        fields.addAll(Arrays.asList(NEWS_CATEGORIES.fields()));
        fields.add(count());

        SelectOnConditionStep<Record> step = ctx.select(fields)
                .from(NEWS)
                .join(USERS).on(NEWS.AUTHOR.equal(USERS.ID))
                .join(NEWS_CATEGORIES).on(NEWS.CATEGORY_ID.equal(NEWS_CATEGORIES.ID));

        joins.forEach(step::join);

        return step.where(conditions)
                .groupBy(groupByClause())
                .orderBy(orderByIdClause())
                .fetch()
                .into(News.class);
    }

    @Override
    public List<News> getLatestAll(int latestCount) {
        return selectClause()
                .groupBy(groupByClause())
                .orderBy(orderByIdClause())
                .limit(latestCount)
                .fetch()
                .into(News.class);
    }

    @Override
    public List<News> getAllFromId(long id, int count) {
        return selectClause()
                .where(pagingWhereClause(id))
                .groupBy(groupByClause())
                .orderBy(orderByIdClause())
                .limit(count)
                .fetch()
                .into(News.class);
    }

    @Override
    public List<News> getLatestByUser(User user, int count) {
        return selectClause()
                .where(authorWhereClause(user))
                .groupBy(groupByClause())
                .orderBy(orderByIdClause())
                .limit(count)
                .fetch()
                .into(News.class);
    }

    @Override
    public List<News> getByUserFromId(User user, Long id, int count) {
        return selectClause()
                .where(pagingWhereClause(id))
                .and(authorWhereClause(user))
                .groupBy(groupByClause())
                .orderBy(orderByIdClause())
                .limit(count)
                .fetch()
                .into(News.class);
    }

    @Override
    public List<News> getLatestBySubscription(Subscription subscription, int count) {
        List<Long> authorsIds = subscription.getAuthors().stream()
                .map(User::getId)
                .collect(Collectors.toList());

        return selectClause()
                .where(NEWS.AUTHOR.in(authorsIds))
                .groupBy(groupByClause())
                .orderBy(orderByIdClause())
                .limit(count)
                .fetch()
                .into(News.class);
    }

    @Override
    public List<News> getBySubscriptionFromId(Subscription subscription, Long id, int count) {
        List<Long> authorsIds = subscription.getAuthors().stream()
                .map(User::getId)
                .collect(Collectors.toList());

        return selectClause()
                .where(NEWS.AUTHOR.in(authorsIds))
                .and(pagingWhereClause(id))
                .groupBy(groupByClause())
                .orderBy(orderByIdClause())
                .limit(count)
                .fetch()
                .into(News.class);
    }

    @Override
    public List<News> getLatestFavourites(User user, int count) {
        return selectClause()
                .join(FAVOURITES).on(FAVOURITES.NEWS_ID.equal(NEWS.ID))
                .where(FAVOURITES.USER_ID.equal(user.getId()))
                .groupBy(groupByClause())
                .orderBy(orderByIdClause())
                .limit(count)
                .fetch()
                .into(News.class);
    }

    @Override
    public List<News> getFavouritesFromId(User user, Long id, int count) {
        return selectClause()
                .join(FAVOURITES).on(FAVOURITES.NEWS_ID.equal(NEWS.ID))
                .where(FAVOURITES.USER_ID.equal(user.getId()))
                .and(pagingWhereClause(id))
                .groupBy(groupByClause())
                .orderBy(orderByIdClause())
                .limit(count)
                .fetch()
                .into(News.class);
    }

    private SelectOnConditionStep<?> selectClause() {
        List<Field<?>> fields = new ArrayList<>(Arrays.asList(NEWS.fields()));
        fields.addAll(Arrays.asList(USERS.fields()));
        fields.addAll(Arrays.asList(NEWS_CATEGORIES.fields()));
        fields.add(count(LIKES.ID));

        return ctx.select(fields)
                .from(NEWS)
                .join(USERS).on(NEWS.AUTHOR.equal(USERS.ID))
                .join(NEWS_CATEGORIES).on(NEWS.CATEGORY_ID.equal(NEWS_CATEGORIES.ID))
                .leftOuterJoin(LIKES).on(NEWS.ID.equal(LIKES.NEWS_ID));
    }

    private Collection<Field<?>> groupByClause() {
        return Arrays.asList(NEWS.ID, USERS.ID, NEWS_CATEGORIES.ID);
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
}
