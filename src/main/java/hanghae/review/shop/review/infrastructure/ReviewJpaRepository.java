package hanghae.review.shop.review.infrastructure;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {

    @Query("select re.id from ReviewEntity re join re.productEntity pe where pe.id = :productId and re.userId = :userId")
    Optional<Long> findReviewByUser(@Param("productId") Long productId, @Param("userId") Long userId);
}
