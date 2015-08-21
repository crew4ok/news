package ru.uruydas.ads.dao.impl;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.uruydas.ads.dao.AdsTypeDao;
import ru.uruydas.ads.model.AdsCategory;
import ru.uruydas.ads.model.AdsType;
import ru.uruydas.common.dao.exception.NotFoundException;
import ru.uruydas.tables.records.AdsTypesRecord;

import java.util.List;

import static ru.uruydas.tables.AdsTypesTable.ADS_TYPES;

@Repository
public class AdsTypeDaoImpl implements AdsTypeDao {
    @Autowired
    private DSLContext ctx;

    @Override
    public AdsType getById(Long adsTypeId) {
        AdsTypesRecord adsTypesRecord = ctx.selectFrom(ADS_TYPES)
                .where(ADS_TYPES.ID.equal(adsTypeId))
                .fetchOne();

        if (adsTypesRecord != null) {
            return adsTypesRecord.into(AdsType.class);
        }

        throw new NotFoundException(AdsType.class, adsTypeId);
    }

    @Override
    public List<AdsType> getByCategory(AdsCategory adsCategory) {
        return ctx.selectFrom(ADS_TYPES)
                .where(ADS_TYPES.CATEGORY_ID.equal(adsCategory.getId()))
                .fetch()
                .into(AdsType.class);
    }

    @Override
    public AdsType create(AdsType adsType) {
        Long id = ctx.insertInto(ADS_TYPES)
                .set(ADS_TYPES.NAME, adsType.getName())
                .set(ADS_TYPES.CATEGORY_ID, adsType.getAdsCategoryId())
                .returning(ADS_TYPES.ID)
                .fetchOne()
                .getId();

        return getById(id);
    }
}
