package urujdas.dao.impl;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urujdas.dao.NewsCategoryDao;
import urujdas.model.NewsCategory;

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
}
