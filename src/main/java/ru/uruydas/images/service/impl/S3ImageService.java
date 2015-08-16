package ru.uruydas.images.service.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.uruydas.common.service.exception.ImageRetrievalFailedException;
import ru.uruydas.common.service.exception.ImageStoringFailedException;
import ru.uruydas.common.util.Validation;
import ru.uruydas.images.dao.ImageDao;
import ru.uruydas.images.model.Image;
import ru.uruydas.images.service.ImageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class S3ImageService implements ImageService {

    private static final String S3_BUCKET_NAME = "uruydas-images";

    private final AmazonS3 s3;

    @Autowired
    private ImageDao imageDao;

    @Autowired
    public S3ImageService(AmazonS3 s3Client) {
        this.s3 = s3Client;
    }

    @Override
    public Image getById(Long imageId) {
        Validation.isGreaterThanZero(imageId);

        Image image = imageDao.getById(imageId);

        try {
            S3Object object = s3.getObject(new GetObjectRequest(
                    S3_BUCKET_NAME,
                    image.getId().toString()
            ));

            int imageSize = (int) object.getObjectMetadata().getContentLength();
            byte[] content = new byte[imageSize];
            int offset = 0;

            try (S3ObjectInputStream objectContent = object.getObjectContent()) {
                int bytesRead;
                do {
                    bytesRead = objectContent.read(content, offset, imageSize);
                    if (bytesRead == -1) {
                        break;
                    }
                    offset += bytesRead;
                } while (offset < imageSize);

                if (offset < imageSize) {
                    throw new ImageRetrievalFailedException(
                            "Failed to read stream from S3;"
                                    + " bytes read = " + offset
                                    + " image size = " + imageSize
                    );
                }
            }

            return Image.fromImage(image)
                    .withContent(content)
                    .build();
        } catch (AmazonClientException | IOException e) {
            throw new ImageRetrievalFailedException("Error while retrieving image with id = " + imageId, e);
        }
    }

    @Override
    public Image save(byte[] content, String contentType) {
        Validation.isNotNull(content);
        Validation.isNotNull(contentType);

        Image image = Image.builder()
                .withContentType(contentType)
                .build();

        Image savedImage = imageDao.save(image);

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(content.length);
            metadata.setContentType(contentType);

            s3.putObject(
                    new PutObjectRequest(
                            S3_BUCKET_NAME,
                            savedImage.getId().toString(),
                            new ByteArrayInputStream(content),
                            metadata
                    )
            );
        } catch (AmazonClientException e) {
            throw new ImageStoringFailedException("Failed to store image to S3", e);
        }

        return savedImage;
    }
}
