package hanghae.review.shop.product.service;

import hanghae.review.global.exception.CustomApiException;
import hanghae.review.global.util.ErrorMessage;
import hanghae.review.shop.product.domain.Product;
import hanghae.review.shop.product.service.port.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 상품을 저장한다
     */
    @Transactional
    public void create(Product product) {
        productRepository.save(product);
    }

    /**
     * Id에 해당하는 상품을 가져온다
     */
    public Product fetchProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new CustomApiException(ErrorMessage.NOT_FOUND_PRODUCT.getMessage()));
    }

}
