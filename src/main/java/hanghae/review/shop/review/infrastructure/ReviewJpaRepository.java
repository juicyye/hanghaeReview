package hanghae.review.shop.review.infrastructure;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {

    @Query("select re from ReviewEntity re join re.productEntity pe where pe.id = :productId")
    List<ReviewEntity> findAllByProductId(@Param("productId") Long productId);

    @Query("select re.id from ReviewEntity re join re.productEntity pe where pe.id = :productId and re.userId = :userId")
    Optional<Long> findReviewByUser(@Param("productId") Long productId, @Param("userId") Long userId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select re from ReviewEntity re join re.productEntity pe where pe.id = :productId")
    List<ReviewEntity> findAllByOptimistic(@Param("productId") Long productId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select re from ReviewEntity re join re.productEntity pe where pe.id = :productId")
    List<ReviewEntity> findAllByPessimistic(@Param("productId") Long productId);
}
