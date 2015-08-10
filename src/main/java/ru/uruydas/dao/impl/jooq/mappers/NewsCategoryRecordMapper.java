package ru.uruydas.dao.impl.jooq.mappers;

import org.jooq.Record;
import org.jooq.RecordMapper;
import ru.uruydas.model.news.NewsCategory;

import static ru.uruydas.tables.NewsCategoriesTable.NEWS_CATEGORIES;

public class NewsCategoryRecordMapper implements RecordMapper<Record, NewsCategory> {
    @Override
    public NewsCategory map(Record record) {
        Long id = record.getValue(NEWS_CATEGORIES.ID);
        String name = record.getValue(NEWS_CATEGORIES.NAME);

        return new NewsCategory(id, name);
    }
}
