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

    @Transactional
    public void create(Product product) {
        productRepository.save(product);
    }

    public Product fetchProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new CustomApiException(ErrorMessage.NOT_FOUND_PRODUCT.getMessage()));
    }

}
