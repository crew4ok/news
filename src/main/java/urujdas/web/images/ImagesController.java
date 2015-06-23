package urujdas.web.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import urujdas.model.images.Image;
import urujdas.service.ImageService;
import urujdas.web.common.WebCommons;
import urujdas.web.images.exception.InvalidImageException;
import urujdas.web.images.model.SaveImageResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(WebCommons.VERSION_PREFIX + "/images")
public class ImagesController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/{imageId}", method = RequestMethod.GET, produces = {
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_JPEG_VALUE
    })
    public byte[] getImageById(@PathVariable("imageId") Long imageId,
                               HttpServletResponse response) {
        Image image = imageService.getById(imageId);

        response.setContentType(image.getContentType());
        return image.getContent();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SaveImageResponse saveImage(@RequestParam("image") MultipartFile imageFile,
                                       @RequestParam("contentType") String contentType) {
        if (imageFile.isEmpty()) {
            throw new InvalidImageException("Image is empty");
        }

        try {
            byte[] bytes = imageFile.getBytes();

            Image image = imageService.save(bytes, contentType);

            return new SaveImageResponse(image.getId());
        } catch (IOException e) {
            throw new InvalidImageException("Error while saving an image", e);
        }
    }


}
