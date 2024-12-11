package hanghae.review.shop.review.infrastructure;

import hanghae.review.shop.review.domain.Review;
import hanghae.review.shop.review.service.port.ReviewRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository jpaRepository;

    @Override
    public void save(Review review) {

    }

    @Override
    public List<Review> findAllByProduct(Long productId) {
        return List.of();
    }

    @Override
    public boolean isReviewAlreadyWritten(Long userId, Long productId) {
        return false;
    }

    @Override
    public Optional<Review> findById(Long id) {
        return Optional.empty();
    }
}
