package urujdas.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import urujdas.dao.ImageDao;
import urujdas.model.images.Image;
import urujdas.service.exception.ImageRetrievalFailedException;
import urujdas.service.exception.ImageStoringFailedException;
import urujdas.service.impl.S3ImageService;
import urujdas.util.exception.InvalidParamException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

@ContextConfiguration(classes = ImageServiceTest.LocalContext.class)
public class ImageServiceTest extends BaseServiceTest {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private AmazonS3 s3Client;

    @AfterMethod
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(imageDao, s3Client);
        reset(imageDao, s3Client);
    }

    @Test(expectedExceptions = InvalidParamException.class)
    public void getById_nullId() throws Exception {
        imageService.getById(null);
    }

    @Test(expectedExceptions = InvalidParamException.class)
    public void getById_negativeId() throws Exception {
        imageService.getById(-1L);
    }

    @Test(expectedExceptions = InvalidParamException.class)
    public void getById_zeroId() throws Exception {
        imageService.getById(0L);
    }

    @Test
    public void getById_hp() throws Exception {
        Long imageId = 1L;
        byte[] content = "content".getBytes();

        Image image = Image.builder()
                .withId(imageId)
                .withContentType("contentType")
                .build();
        when(imageDao.getById(imageId)).thenReturn(image);

        S3Object s3Object = mock(S3Object.class);
        when(s3Client.getObject(any(GetObjectRequest.class))).thenReturn(s3Object);

        ObjectMetadata metadata = mock(ObjectMetadata.class);
        when(metadata.getContentLength()).thenReturn((long) content.length);
        when(s3Object.getObjectMetadata()).thenReturn(metadata);

        S3ObjectInputStream inputStream = mock(S3ObjectInputStream.class);
        when(inputStream.read(any(byte[].class), anyInt(), anyInt()))
                .thenReturn(content.length)
                .thenReturn(-1);
        when(s3Object.getObjectContent()).thenReturn(inputStream);

        Image actualImage = imageService.getById(imageId);

        verify(imageDao).getById(imageId);

        ArgumentCaptor<GetObjectRequest> requestArgumentCaptor = ArgumentCaptor.forClass(GetObjectRequest.class);
        verify(s3Client).getObject(requestArgumentCaptor.capture());

        GetObjectRequest request = requestArgumentCaptor.getValue();
        assertEquals(request.getBucketName(), "urujdas-images");
        assertEquals(request.getKey(), image.getId().toString());
    }

    @Test(expectedExceptions = ImageRetrievalFailedException.class)
    public void getById_amazonException() throws Exception {
        Long imageId = 1L;

        Image image = Image.builder()
                .withId(imageId)
                .withContentType("contentType")
                .build();
        when(imageDao.getById(imageId)).thenReturn(image);

        when(s3Client.getObject(any(GetObjectRequest.class))).thenThrow(new AmazonClientException("exception"));

        try {
            imageService.getById(imageId);
        } finally {
            verify(imageDao).getById(imageId);

            ArgumentCaptor<GetObjectRequest> requestArgumentCaptor = ArgumentCaptor.forClass(GetObjectRequest.class);
            verify(s3Client).getObject(requestArgumentCaptor.capture());

            GetObjectRequest request = requestArgumentCaptor.getValue();
            assertEquals(request.getBucketName(), "urujdas-images");
            assertEquals(request.getKey(), imageId.toString());
        }
    }

    @Test(expectedExceptions = InvalidParamException.class)
    public void save_nullContent() throws Exception {
        imageService.save(null, "contentType");
    }

    @Test(expectedExceptions = InvalidParamException.class)
    public void save_nullContentType() throws Exception {
        imageService.save(new byte[0], null);
    }

    @Test
    public void save_hp() throws Exception {
        byte[] content = new byte[8];
        String contentType = "contentType";

        Image image = Image.builder()
                .withContentType(contentType)
                .build();
        Image savedImage = Image.fromImage(image)
                .withId(1L)
                .build();
        when(imageDao.save(image)).thenReturn(savedImage);

        Image actualSavedImage = imageService.save(content, contentType);

        assertEquals(actualSavedImage, savedImage);

        verify(imageDao).save(image);

        ArgumentCaptor<PutObjectRequest> argumentCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
        verify(s3Client).putObject(argumentCaptor.capture());

        PutObjectRequest actualRequest = argumentCaptor.getValue();

        assertEquals(actualRequest.getBucketName(), "urujdas-images");
        assertEquals(actualRequest.getKey(), savedImage.getId().toString());
        assertEquals(actualRequest.getMetadata().getContentLength(), content.length);
        assertEquals(actualRequest.getMetadata().getContentType(), contentType);
    }

    @Test(expectedExceptions = ImageStoringFailedException.class)
    public void save_exceptionFromAmazon() throws Exception {
        byte[] content = new byte[8];
        String contentType = "contentType";

        Image image = Image.builder()
                .withContentType(contentType)
                .build();
        Image savedImage = Image.fromImage(image)
                .withId(1L)
                .build();
        when(imageDao.save(image)).thenReturn(savedImage);

        when(s3Client.putObject(any(PutObjectRequest.class))).thenThrow(new AmazonClientException("exception"));

        try {
            imageService.save(content, contentType);
        } finally {
            verify(imageDao).save(image);
            verify(s3Client).putObject(any(PutObjectRequest.class));
        }
    }

    @Configuration
    static class LocalContext {
        @Bean
        public ImageService imageService(AmazonS3 s3Client) {
            return new S3ImageService(s3Client);
        }

        @Bean
        public ImageDao imageDao() {
            return mock(ImageDao.class);
        }
    }
}
