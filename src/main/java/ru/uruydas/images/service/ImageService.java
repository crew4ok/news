package ru.uruydas.images.service;

import ru.uruydas.images.model.Image;

public interface ImageService {
    Image getById(Long imageId);

    Image save(byte[] bytes, String contentType);
}
