package hanghae.review.shop.product.service;

import hanghae.review.global.exception.CustomApiException;
import hanghae.review.global.util.ErrorMessage;
import hanghae.review.shop.product.domain.Product;
import hanghae.review.shop.product.event.ProductIncreaseEvent;
import hanghae.review.shop.product.service.port.ProductRepository;
import hanghae.review.shop.review.domain.ReviewUpdate;
import hanghae.review.shop.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductMetricsService {

    private final ProductRepository productRepository;
    private final ReviewService reviewService;

    // todo 락 걸기, Facade, Redisson 사용하기
    @Transactional
    public void updateReviewMetrics(ProductIncreaseEvent event) {
        Product product = getProduct(event.productId());
        ReviewUpdate totalReviewScore = reviewService.getTotalReviewScore(event.productId());
        product.updateReviewData(totalReviewScore.count() + 1, totalReviewScore.totalScore() + event.score());
        productRepository.save(product);
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new CustomApiException(ErrorMessage.NOT_FOUND_PRODUCT.getMessage())
        );
    }
}
