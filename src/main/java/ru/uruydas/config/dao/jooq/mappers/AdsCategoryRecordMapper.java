package ru.uruydas.config.dao.jooq.mappers;

import org.jooq.Record;
import org.jooq.RecordMapper;
import ru.uruydas.ads.model.AdsCategory;
import ru.uruydas.tables.AdsCategoriesTable;

import static ru.uruydas.tables.AdsCategoriesTable.ADS_CATEGORIES;

public class AdsCategoryRecordMapper implements RecordMapper<Record, AdsCategory> {
    private static final String ALIAS_PREFIX = "t";

    @Override
    public AdsCategory map(Record record) {
        String categoryAlias = ALIAS_PREFIX + "1";
        String subCategoryAlias = ALIAS_PREFIX + "2";

        AdsCategoriesTable t1 = ADS_CATEGORIES.as(categoryAlias);
        AdsCategoriesTable t2 = ADS_CATEGORIES.as(subCategoryAlias);

        Long parentCategoryId = record.getValue(t1.PARENT_CATEGORY);

        AdsCategory parentCategory = null;
        if (parentCategoryId != null) {
            parentCategory = new AdsCategory(
                    record.getValue(t2.ID),
                    record.getValue(t2.NAME),
                    null
            );
        }

        return new AdsCategory(
                record.getValue(t1.ID),
                record.getValue(t1.NAME),
                parentCategory
        );
    }
}
