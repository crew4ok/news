package ru.uruydas.config.dao.jooq.mappers;

import org.jooq.Record;
import org.jooq.RecordMapper;
import ru.uruydas.ads.model.AdsType;

import static ru.uruydas.tables.AdsTypesTable.ADS_TYPES;

public class AdsTypeRecordMapper implements RecordMapper<Record, AdsType> {
    @Override
    public AdsType map(Record record) {
        return new AdsType(
                record.getValue(ADS_TYPES.ID),
                record.getValue(ADS_TYPES.NAME),
                record.getValue(ADS_TYPES.CATEGORY_ID)
        );
    }
}
