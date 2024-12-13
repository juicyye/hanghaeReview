package hanghae.review.shop.product.service.lock;

import hanghae.review.global.exception.CustomApiException;
import hanghae.review.global.util.ErrorMessage;
import hanghae.review.shop.product.domain.Product;
import hanghae.review.shop.product.service.port.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptimisticService {

    private final ProductRepository productRepository;

    public void updateOptimistic(Long productId, Float score) {
        Product product = getProduct(productId);
        product.updateReviewData(score);
        productRepository.save(product);
    }

    private Product getProduct(Long productId) {
        return productRepository.findProductOptimistic(productId).orElseThrow(
                () -> new CustomApiException(ErrorMessage.NOT_FOUND_PRODUCT.getMessage())
        );
    }


}