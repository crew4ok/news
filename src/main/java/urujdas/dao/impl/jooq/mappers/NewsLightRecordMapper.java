package urujdas.dao.impl.jooq.mappers;

import org.jooq.Record;
import org.jooq.RecordMapper;
import urujdas.model.news.NewsLight;

import static urujdas.tables.NewsTable.NEWS;
import static urujdas.tables.UsersTable.USERS;

public class NewsLightRecordMapper implements RecordMapper<Record, NewsLight> {
    @Override
    public NewsLight map(Record record) {
        return NewsLight.builder()
                .withId(record.getValue(NEWS.ID))
                .withTitle(record.getValue(NEWS.TITLE))
                .withBody(record.getValue(NEWS.BODY))
                .withUsername(record.getValue(USERS.USERNAME))
                .withFirstname(record.getValue(USERS.FIRSTNAME))
                .withLastname(record.getValue(USERS.LASTNAME))
                .withCommentsCount(record.getValue("comments_count", Integer.class))
                .withLikesCount(record.getValue("likes_count", Integer.class))
                .withCurrentUserFavoured(record.getValue("user_favoured", Boolean.class))
                .withCurrentUserLiked(record.getValue("user_liked", Boolean.class))
                .build();
    }
}
