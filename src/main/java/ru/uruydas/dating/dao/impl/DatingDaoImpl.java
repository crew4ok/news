package ru.uruydas.dating.dao.impl;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.uruydas.dating.dao.DatingDao;
import ru.uruydas.tables.records.UserFiltersRecord;
import ru.uruydas.users.model.AgeRange;
import ru.uruydas.users.model.User;
import ru.uruydas.users.model.UserFilter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.uruydas.common.util.MapperUtils.fromNullable;
import static ru.uruydas.tables.UserFiltersTable.USER_FILTERS;
import static ru.uruydas.tables.UsersTable.USERS;

@Repository
public class DatingDaoImpl implements DatingDao {

    @Autowired
    private DSLContext ctx;

    @Override
    public List<User> getLatestUsersByFilter(UserFilter filter, int count) {
        List<Condition> conditions = createConditionsFromFilter(filter);

        return ctx.selectFrom(USERS)
                .where(conditions)
                .orderBy(orderByExpression())
                .limit(count)
                .fetch()
                .into(User.class);
    }

    @Override
    public List<User> getUsersByFilterFromDate(UserFilter filter, LocalDateTime pullUpDate, int count) {
        List<Condition> conditions = createConditionsFromFilter(filter);

        return ctx.selectFrom(USERS)
                .where(conditions)
                .and(USERS.PULL_UP_DATE.lessThan(Timestamp.valueOf(pullUpDate)))
                .orderBy(orderByExpression())
                .limit(count)
                .fetch()
                .into(User.class);
    }

    private List<Condition> createConditionsFromFilter(UserFilter filter) {
        List<Condition> conditions = new ArrayList<>();

        conditions.add(USERS.ID.notEqual(filter.getUserId()));

        if (filter.getGender() != null) {
            conditions.add(USERS.GENDER.equal(filter.getGender().name()));
        }
        if (filter.getGenderPreferences() != null) {
            conditions.add(USERS.GENDER_PREFERENCES.equal(filter.getGenderPreferences().name()));
        }
        if (filter.getRelationsPreferences() != null) {
            conditions.add(USERS.RELATIONS_PREFERENCES.equal(filter.getRelationsPreferences().name()));
        }
        if (filter.getAgeRange() != null) {
            String condition = "extract(year from age(current_timestamp at time zone 'utc', birth_date)) %s ?";

            if (filter.getAgeRange().getLower() != null) {
                conditions.add(
                        DSL.condition(String.format(condition, ">"), filter.getAgeRange().getLower())
                );
            }
            if (filter.getAgeRange().getHigher() != null) {
                conditions.add(
                        DSL.condition(String.format(condition, "<"), filter.getAgeRange().getHigher())
                );
            }
        }
        return conditions;
    }

    private List<SortField<?>> orderByExpression() {
        return Arrays.asList(USERS.PULL_UP_DATE.desc(), USERS.ID.desc());
    }

    @Override
    public UserFilter findUserFilter(User user) {
        UserFiltersRecord record = ctx.selectFrom(USER_FILTERS)
                .where(USER_FILTERS.USER_ID.equal(user.getId()))
                .fetchOne();

        if (record != null) {
            return record.into(UserFilter.class);
        }
        return null;
    }

    @Override
    public void updateUserFilter(User user, UserFilter userFilter) {
        int updatedRows = ctx.update(USER_FILTERS)
                .set(getMapping(userFilter))
                .where(USER_FILTERS.USER_ID.equal(user.getId()))
                .execute();

        if (updatedRows == 0) {
            ctx.insertInto(USER_FILTERS)
                    .set(getMapping(userFilter))
                    .set(USER_FILTERS.USER_ID, user.getId())
                    .execute();
        }
    }

    private Map<Field<?>, Object> getMapping(UserFilter userFilter) {
        Map<Field<?>, Object> map = new HashMap<>();
        map.put(USER_FILTERS.GENDER, fromNullable(userFilter.getGender(), Enum::name));
        map.put(USER_FILTERS.GENDER_PREFERENCES, fromNullable(userFilter.getGenderPreferences(), Enum::name));
        map.put(USER_FILTERS.RELATIONS_PREFERENCES, fromNullable(userFilter.getRelationsPreferences(), Enum::name));
        map.put(USER_FILTERS.AGE_LOWER, fromNullable(userFilter.getAgeRange(), AgeRange::getLower));
        map.put(USER_FILTERS.AGE_HIGHER, fromNullable(userFilter.getAgeRange(), AgeRange::getHigher));
        return map;
    }

    @Override
    public void pullUserUp(User user) {
        ctx.query("update uruydas.users" +
                        " set pull_up_date = current_timestamp at time zone 'utc'" +
                        " where id = ?",
                user.getId())
                .execute();
    }
}
