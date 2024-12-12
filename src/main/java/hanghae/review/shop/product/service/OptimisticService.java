package hanghae.review.shop.product.service;

import hanghae.review.global.exception.CustomApiException;
import hanghae.review.global.util.ErrorMessage;
import hanghae.review.shop.product.domain.Product;
import hanghae.review.shop.product.service.port.ProductRepository;
import hanghae.review.shop.review.domain.Review;
import hanghae.review.shop.review.service.port.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptimisticService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    // 낙관적 락
    @Transactional
    public void updateOptimistic(Long productId) {
        Product product = getProduct(productId);
        ReviewUpdate totalReviewScore = getTotalReviewScore(productId);
        product.updateReviewData(totalReviewScore.count(), totalReviewScore.totalScore());
        productRepository.modifyProductReviewStats(product.getReviewCount(), product.getScore(), product.getId());
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new CustomApiException(ErrorMessage.NOT_FOUND_PRODUCT.getMessage())
        );
    }

    private ReviewUpdate getTotalReviewScore(Long productId) {
        List<Review> reviews = reviewRepository.findAllByOptimistic(productId);
        long count = reviews.size();
        float totalScore = (float) reviews.stream()
                .mapToDouble(Review::getScore)
                .sum();
        return new ReviewUpdate(count, totalScore);
    }

    record ReviewUpdate(long count, float totalScore) {
    }
}