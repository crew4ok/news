package ru.uruydas.config.dao.jooq.mappers;

import org.jooq.Record;
import org.jooq.RecordMapper;
import ru.uruydas.ads.model.Ads;
import ru.uruydas.ads.model.AdsType;
import ru.uruydas.users.model.User;

import java.sql.Timestamp;

import static ru.uruydas.common.util.MapperUtils.fromNullable;
import static ru.uruydas.tables.AdsTable.ADS;

public class AdsRecordMapper implements RecordMapper<Record, Ads> {
    @Override
    public Ads map(Record record) {
        User author = record.into(User.class);
        AdsType adsType = record.into(AdsType.class);

        return Ads.builder()
                .withId(record.getValue(ADS.ID))
                .withTitle(record.getValue(ADS.TITLE))
                .withDescription(record.getValue(ADS.DESCRIPTION))
                .withAdsType(adsType)
                .withCreationDate(fromNullable(record.getValue(ADS.CREATION_DATE), Timestamp::toLocalDateTime))
                .withPhone(record.getValue(ADS.PHONE))
                .withEmail(record.getValue(ADS.EMAIL))
                .withCity(record.getValue(ADS.CITY))
                .withPrice(record.getValue(ADS.PRICE))
                .withAuthor(author)
                .build();
    }
}
