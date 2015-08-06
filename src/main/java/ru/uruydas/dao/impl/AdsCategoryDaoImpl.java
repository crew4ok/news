package ru.uruydas.dao.impl;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.uruydas.dao.AdsCategoryDao;
import ru.uruydas.tables.AdsCategoriesTable;
import urujdas.dao.exception.NotFoundException;
import ru.uruydas.model.ads.AdsCategory;
import ru.uruydas.tables.records.AdsCategoriesRecord;

import java.util.List;

@Repository
public class AdsCategoryDaoImpl implements AdsCategoryDao {

    @Autowired
    private DSLContext ctx;

    @Override
    public AdsCategory getById(Long id) {
        AdsCategoriesRecord adsCategoriesRecord = ctx.selectFrom(AdsCategoriesTable.ADS_CATEGORIES)
                .where(AdsCategoriesTable.ADS_CATEGORIES.ID.equal(id))
                .fetchOne();

        if (adsCategoriesRecord != null) {
            return adsCategoriesRecord.into(AdsCategory.class);
        }

        throw new NotFoundException(AdsCategory.class, id);
    }

    @Override
    public List<AdsCategory> getAll() {
        return ctx.selectFrom(AdsCategoriesTable.ADS_CATEGORIES)
                .fetch()
                .into(AdsCategory.class);
    }
}
