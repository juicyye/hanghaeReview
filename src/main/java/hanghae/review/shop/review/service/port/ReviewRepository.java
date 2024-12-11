package hanghae.review.shop.review.service.port;

import hanghae.review.shop.review.controller.resp.ProductReviewRespDto;
import hanghae.review.shop.review.domain.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface ReviewRepository {
    void save(Review review);
    List<Review> findAllByProduct(Long productId);

    boolean isReviewAlreadyWritten(Long userId, Long productId);
    Optional<Review> findById(Long id);

    ProductReviewRespDto findProductReview(Long productId, Pageable pageable);
}
