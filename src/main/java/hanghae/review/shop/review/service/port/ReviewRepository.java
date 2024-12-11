package hanghae.review.shop.review.service.port;

import hanghae.review.shop.review.domain.Review;
import java.util.List;

public interface ReviewRepository {
    void save(Review review);
    List<Review> findAllByProduct(Long productId);

    boolean isReviewAlreadyWritten(Long userId, Long productId);
}
