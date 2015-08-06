package ru.uruydas.dao.impl.jooq.mappers;

import org.jooq.Record;
import org.jooq.RecordMapper;
import ru.uruydas.tables.AdsCategoriesTable;
import ru.uruydas.model.ads.AdsCategory;

public class AdsCategoryRecordMapper implements RecordMapper<Record, AdsCategory> {
    @Override
    public AdsCategory map(Record record) {
        return new AdsCategory(
                record.getValue(AdsCategoriesTable.ADS_CATEGORIES.ID),
                record.getValue(AdsCategoriesTable.ADS_CATEGORIES.NAME)
        );
    }
}
