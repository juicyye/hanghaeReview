package hanghae.review.shop.product.service;

import hanghae.review.global.exception.CustomApiException;
import hanghae.review.global.util.ErrorMessage;
import hanghae.review.shop.product.domain.Product;
import hanghae.review.shop.product.service.port.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductMetricsService {

    private final ProductRepository productRepository;

    /**
     * 메서드가 호출되면 해당 점수만큼 총 점수와 리뷰수가 오르게 된다
     */
    public void updateReviewMetrics(Long productId, Integer score) {
        Product product = getProduct(productId);
        product.updateReviewData(score);
        productRepository.save(product);
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new CustomApiException(ErrorMessage.NOT_FOUND_PRODUCT.getMessage())
        );
    }

}
