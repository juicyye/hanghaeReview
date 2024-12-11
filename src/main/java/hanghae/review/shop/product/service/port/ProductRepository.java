package hanghae.review.shop.product.service.port;

import hanghae.review.shop.product.domain.Product;
import java.util.Optional;

public interface ProductRepository {

    void save(Product product);
    Optional<Product> findById(Long id);
    void deleteById(Long id);
}
