package hanghae.review.shop.imagefile.service.port;

import hanghae.review.shop.imagefile.domain.ImageFile;
import java.util.Optional;

public interface ImageFileRepository {

    void save(ImageFile imageFile);
    Optional<ImageFile> findById(Long id);
}
