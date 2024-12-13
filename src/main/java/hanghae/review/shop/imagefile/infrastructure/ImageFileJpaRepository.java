package hanghae.review.shop.imagefile.infrastructure;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageFileJpaRepository extends JpaRepository<ImageFileEntity, Long> {
    @Query("select if.originalFileName from ImageFileEntity if join if.reviewEntity re where re.id = :id")
    Optional<String> findByReviewId(@Param("id") Long reviewId);
}
