package ru.uruydas.config.dao.jooq.mappers;

import org.jooq.Record;
import org.jooq.RecordMapper;
import ru.uruydas.ads.model.AdsCategory;

import static ru.uruydas.tables.AdsCategoriesTable.ADS_CATEGORIES;

public class AdsCategoryRecordMapper implements RecordMapper<Record, AdsCategory> {

    @Override
    public AdsCategory map(Record record) {
        return new AdsCategory(
                record.getValue(ADS_CATEGORIES.ID),
                record.getValue(ADS_CATEGORIES.NAME),
                record.getValue(ADS_CATEGORIES.PARENT_CATEGORY)
        );
    }
}
