package ru.uruydas.ads.dao.impl;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.uruydas.ads.dao.AdsCategoryDao;
import ru.uruydas.ads.model.Ads;
import ru.uruydas.ads.model.AdsCategory;
import ru.uruydas.common.dao.exception.NotFoundException;
import ru.uruydas.tables.AdsCategoriesTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.uruydas.common.util.MapperUtils.fromNullable;
import static ru.uruydas.tables.AdsCategoriesTable.ADS_CATEGORIES;
import static ru.uruydas.tables.AdsTable.ADS;

@Repository
public class AdsCategoryDaoImpl implements AdsCategoryDao {

    @Autowired
    private DSLContext ctx;

    @Override
    public AdsCategory getById(Long id) {
        AdsCategoriesTable t1 = ADS_CATEGORIES.as("t1");
        AdsCategoriesTable t2 = ADS_CATEGORIES.as("t2");

        Record record = ctx
                .select(combineFields(t1.fields(), t2.fields()))
                .from(t1)
                .leftOuterJoin(t2).on(t1.PARENT_CATEGORY.equal(t2.ID))
                .where(t1.ID.equal(id))
                .fetchOne();

        if (record != null) {
            return record.into(AdsCategory.class);
        }

        throw new NotFoundException(AdsCategory.class, id);
    }

    @Override
    public AdsCategory getByAds(Ads ads) {
        AdsCategoriesTable t1 = ADS_CATEGORIES.as("t1");
        AdsCategoriesTable t2 = ADS_CATEGORIES.as("t2");

        Record record = ctx
                .select(combineFields(t1.fields(), t2.fields()))
                .from(ADS)
                .join(t1).on(ADS.SUBCATEGORY_ID.equal(t1.ID))
                .leftOuterJoin(t2).on(t1.PARENT_CATEGORY.equal(t2.ID))
                .where(ADS.ID.equal(ads.getId()))
                .fetchOne();

        if (record != null) {
            return record.into(AdsCategory.class);
        }

        throw new NotFoundException(AdsCategory.class, ads);
    }

    private List<Field<?>> combineFields(Field<?>[] arr1, Field<?>[] arr2) {
        List<Field<?>> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(arr1));
        fields.addAll(Arrays.asList(arr2));
        return fields;
    }

    @Override
    public List<AdsCategory> getAllCategories() {
        return ctx.selectFrom(ADS_CATEGORIES)
                .where(ADS_CATEGORIES.PARENT_CATEGORY.isNull())
                .orderBy(ADS_CATEGORIES.NAME.asc())
                .fetch()
                .into(AdsCategory.class);
    }

    @Override
    public List<AdsCategory> getAllSubCategories(AdsCategory category) {
        AdsCategoriesTable t1 = ADS_CATEGORIES.as("t1");
        AdsCategoriesTable t2 = ADS_CATEGORIES.as("t2");

        return ctx
                .select(combineFields(t1.fields(), t2.fields()))
                .from(t1)
                .join(t2).on(t1.PARENT_CATEGORY.equal(t2.ID))
                .orderBy(t1.NAME.asc())
                .fetch()
                .into(AdsCategory.class);
    }

    @Override
    public AdsCategory create(AdsCategory category) {
        Long id = ctx.insertInto(ADS_CATEGORIES)
                .set(ADS_CATEGORIES.NAME, category.getName())
                .set(ADS_CATEGORIES.PARENT_CATEGORY, fromNullable(category.getParentCategory(), AdsCategory::getId))
                .returning(ADS_CATEGORIES.ID)
                .fetchOne()
                .getId();

        return getById(id);
    }
}
