package hanghae.review.shop.imagefile.service.port;

import hanghae.review.shop.imagefile.domain.ImageFile;

public interface ImageFileRepository {

    void save(ImageFile imageFile);
}
