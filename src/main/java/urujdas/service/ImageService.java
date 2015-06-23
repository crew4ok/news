package urujdas.service;

import urujdas.model.images.Image;

public interface ImageService {
    Image getById(Long imageId);

    Image save(byte[] bytes, String contentType);
}
