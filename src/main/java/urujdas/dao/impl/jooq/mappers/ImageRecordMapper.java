package urujdas.dao.impl.jooq.mappers;

import org.jooq.Record;
import org.jooq.RecordMapper;
import urujdas.model.images.Image;

import static urujdas.tables.ImagesTable.IMAGES;

public class ImageRecordMapper implements RecordMapper<Record, Image> {
    @Override
    public Image map(Record record) {
        return Image.builder()
                .withId(record.getValue(IMAGES.ID))
                .withContentType(record.getValue(IMAGES.CONTENT_TYPE))
                .build();
    }
}
