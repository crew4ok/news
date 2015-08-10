package ru.uruydas.service;

import ru.uruydas.model.images.Image;

public interface ImageService {
    Image getById(Long imageId);

    Image save(byte[] bytes, String contentType);
}
