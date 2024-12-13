package hanghae.review.shop.review.service.port;

import hanghae.review.shop.review.controller.resp.ReviewRespDto;
import hanghae.review.shop.review.domain.Review;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    Review save(Review review);

    boolean isReviewAlreadyWritten(Long userId, Long productId);

    Optional<Review> findById(Long id);

    List<ReviewRespDto> findProductReview(Long productId, Long cursor, int size);
}
