package hanghae.review.shop.imagefile.infrastructure;

import hanghae.review.shop.imagefile.domain.ImageFile;
import hanghae.review.shop.imagefile.service.port.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageFileRepositoryImpl implements ImageFileRepository {

    private final ImageFileJpaRepository jpaRepository;

    @Override
    public void save(ImageFile imageFile) {
        jpaRepository.save(ImageFileEntity.fromEntity(imageFile));
    }
}
