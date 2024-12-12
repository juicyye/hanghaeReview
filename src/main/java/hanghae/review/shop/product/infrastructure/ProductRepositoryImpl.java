package hanghae.review.shop.product.infrastructure;

import hanghae.review.shop.product.domain.Product;
import hanghae.review.shop.product.service.port.ProductRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository jpaRepository;

    public void save(Product product) {
        jpaRepository.save(ProductEntity.fromModel(product));
    }

    public Optional<Product> findById(Long id) {
        return jpaRepository.findById(id)
                .map(ProductEntity::toModel);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public void modifyProductReviewStats(Long reviewCount, Float score, Long productId) {
        jpaRepository.updateProductScore(reviewCount, score, productId);
    }

}
