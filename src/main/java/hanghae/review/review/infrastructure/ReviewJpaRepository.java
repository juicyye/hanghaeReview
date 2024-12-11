package hanghae.review.review.infrastructure;

import hanghae.review.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {
}
