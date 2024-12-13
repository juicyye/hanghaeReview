package hanghae.review.shop.imagefile.infrastructure;

import hanghae.review.shop.imagefile.domain.ImageFile;
import hanghae.review.shop.imagefile.service.port.ImageFileRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageFileRepositoryImpl implements ImageFileRepository {

    private final ImageFileJpaRepository jpaRepository;

    @Override
    public void save(ImageFile imageFile) {
        ImageFileEntity savedImageFile = jpaRepository.save(ImageFileEntity.fromEntity(imageFile));
    }

    @Override
    public Optional<ImageFile> findById(Long id) {
        return jpaRepository.findById(id).map(ImageFileEntity::toEntity);
    }

    @Override
    public Optional<String> findByReviewId(Long reviewId) {
        return jpaRepository.findByReviewId(reviewId);
    }
}
