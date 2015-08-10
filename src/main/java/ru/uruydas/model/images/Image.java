package ru.uruydas.model.images;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.util.Arrays;

public class Image {
    private final Long id;
    private final String contentType;
    private final byte[] content;

    @GeneratePojoBuilder
    public Image(Long id, String contentType, byte[] content) {
        this.id = id;
        this.contentType = contentType;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (id != null ? !id.equals(image.id) : image.id != null) return false;
        if (contentType != null ? !contentType.equals(image.contentType) : image.contentType != null) return false;
        return Arrays.equals(content, image.content);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
        result = 31 * result + (content != null ? Arrays.hashCode(content) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", contentType='" + contentType + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }

    public static ImageBuilder fromImage(Image image) {
        return new ImageBuilder()
                .withId(image.getId())
                .withContentType(image.getContentType())
                .withContent(image.getContent());
    }

    public static ImageBuilder builder() {
        return new ImageBuilder();
    }
}
