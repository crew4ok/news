package ru.uruydas.dao.impl;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectOnConditionStep;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.uruydas.dao.AdsDao;
import ru.uruydas.dao.exception.NotFoundException;
import ru.uruydas.model.ads.Ads;
import ru.uruydas.model.ads.AdsCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static ru.uruydas.tables.AdsCategoriesTable.ADS_CATEGORIES;
import static ru.uruydas.tables.AdsTable.ADS;
import static ru.uruydas.tables.UsersTable.USERS;

@Repository
public class AdsDaoImpl implements AdsDao {

    @Autowired
    private DSLContext ctx;

    @Override
    public Ads getById(Long id) {
        List<Ads> ads = select(
                singletonList(ADS.ID.equal(id)),
                Optional.<Integer>empty()
        );

        if (!ads.isEmpty()) {
            return ads.get(0);
        }

        throw new NotFoundException(Ads.class, id);
    }

    @Override
    public List<Ads> getLatestByCategory(AdsCategory category, int count) {
        return select(
                emptyList(),
                Optional.of(count)
        );
    }

    @Override
    public List<Ads> getFromIdByCategory(AdsCategory category, Long adsId, int count) {
        return select(
                singletonList(ADS.ID.lessThan(adsId)),
                Optional.of(count)
        );
    }

    @Override
    public List<Ads> searchByTitle(String title) {
        return select(
                singletonList(ADS.TITLE.like(title + "%")),
                Optional.<Integer>empty()
        );
    }

    private List<Ads> select(List<Condition> conditions,
                             Optional<Integer> count) {
        return select(Collections.emptyMap(), conditions, count);
    }

    private List<Ads> select(Map<Table<?>, Condition> joins,
                             List<Condition> conditions,
                             Optional<Integer> count) {

        SelectOnConditionStep<Record> step = ctx.select(fields())
                .from(ADS)
                .join(ADS_CATEGORIES).on(ADS.SUBCATEGORY_ID.equal(ADS_CATEGORIES.ID))
                .join(USERS).on(ADS.AUTHOR.equal(USERS.ID))
                ;

        joins.forEach((t, c) -> step.join(t).on(c));

        step.where(conditions)
                .orderBy(ADS.CREATION_DATE.desc(), ADS.ID.desc());

        if (count.isPresent()) {
            step.limit(count.get());
        }

        return step.fetch()
                .into(Ads.class);
    }

    private List<Field<?>> fields() {
        List<Field<?>> fields = new ArrayList<>(Arrays.asList(ADS.fields()));
        fields.addAll(Arrays.asList(USERS.fields()));
        return fields;
    }

    @Override
    public Ads create(Ads ads) {
        Long id = ctx.insertInto(ADS)
                .set(getMapping(ads))
                .returning(ADS.ID)
                .fetchOne()
                .getId();

        return getById(id);
    }

    @Override
    public Ads update(Ads ads) {
        Long id = ctx.update(ADS)
                .set(getMapping(ads))
                .where(ADS.ID.equal(ads.getId()))
                .returning(ADS.ID)
                .fetchOne()
                .getId();

        return getById(id);
    }

    private Map<Field<?>, Object> getMapping(Ads ads) {
        Map<Field<?>, Object> mapping = new HashMap<>();
        mapping.put(ADS.TITLE, ads.getTitle());
        mapping.put(ADS.DESCRIPTION, ads.getDescription());
        mapping.put(ADS.ADS_TYPE, ads.getAdsType().name());
        mapping.put(ADS.PHONE, ads.getPhone());
        mapping.put(ADS.EMAIL, ads.getEmail());
        mapping.put(ADS.CITY, ads.getCity());
        mapping.put(ADS.PRICE, ads.getPrice());
        mapping.put(ADS.SUBCATEGORY_ID, ads.getAdsCategory().getId());
        mapping.put(ADS.AUTHOR, ads.getAuthor().getId());
        return mapping;
    }
}
