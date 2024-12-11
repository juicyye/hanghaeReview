package hanghae.review.imagefile.infrastructure;

import hanghae.review.imagefile.domain.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageFileJpaRepository extends JpaRepository<ImageFileEntity, Long> {
}
