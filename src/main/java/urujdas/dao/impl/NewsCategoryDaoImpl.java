package urujdas.dao.impl;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urujdas.dao.NewsCategoryDao;
import urujdas.dao.exception.NotFoundException;
import urujdas.model.NewsCategory;
import urujdas.tables.records.NewsCategoriesRecord;

import java.util.List;

import static urujdas.tables.NewsCategoriesTable.NEWS_CATEGORIES;

@Repository
public class NewsCategoryDaoImpl implements NewsCategoryDao {

    @Autowired
    private DSLContext ctx;

    @Override
    public NewsCategory create(NewsCategory category) {
        return ctx.insertInto(NEWS_CATEGORIES)
                .set(NEWS_CATEGORIES.NAME, category.getName())
                .returning(NEWS_CATEGORIES.fields())
                .fetchOne()
                .into(NewsCategory.class);
    }

    @Override
    public NewsCategory getById(Long categoryId) {
        NewsCategoriesRecord record = ctx.selectFrom(NEWS_CATEGORIES)
                .where(NEWS_CATEGORIES.ID.equal(categoryId))
                .fetchOne();

        if (record != null) {
            return record.into(NewsCategory.class);
        }

        throw new NotFoundException(NewsCategory.class, categoryId);
    }

    @Override
    public List<NewsCategory> getAll() {
        return ctx.selectFrom(NEWS_CATEGORIES)
                .fetch()
                .into(NewsCategory.class);
    }
}
