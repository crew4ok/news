package ru.uruydas.dao.impl.jooq.mappers;

import org.jooq.Record;
import org.jooq.RecordMapper;
import ru.uruydas.model.ads.Ads;
import ru.uruydas.model.ads.AdsCategory;
import ru.uruydas.model.ads.AdsType;

import java.sql.Timestamp;

import static ru.uruydas.tables.AdsTable.ADS;
import static urujdas.util.MapperUtils.fromNullable;

public class AdsRecordMapper implements RecordMapper<Record, Ads> {
    @Override
    public Ads map(Record record) {
        AdsCategory category = record.into(AdsCategory.class);

        return Ads.builder()
                .withId(record.getValue(ADS.ID))
                .withTitle(record.getValue(ADS.TITLE))
                .withDescription(record.getValue(ADS.DESCRIPTION))
                .withAdsType(fromNullable(record.getValue(ADS.ADS_TYPE), AdsType::valueOf))
                .withCreationDate(fromNullable(record.getValue(ADS.CREATION_DATE), Timestamp::toLocalDateTime))
                .withPhone(record.getValue(ADS.PHONE))
                .withEmail(record.getValue(ADS.EMAIL))
                .withCity(record.getValue(ADS.CITY))
                .withAdsCategory(category)
                .build();
    }
}
