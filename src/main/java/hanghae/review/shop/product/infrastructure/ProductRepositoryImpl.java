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

    @Override
    public void save(Product product) {
        jpaRepository.save(ProductEntity.fromModel(product));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaRepository.findById(id)
                .map(ProductEntity::toModel);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findProductOptimistic(Long id) {
        return jpaRepository.fetchProductByIdOptimistic(id)
                .map(ProductEntity::toModel);
    }

    @Override
    public Optional<Product> findProductPessimistic(Long id) {
        return jpaRepository.fetchProductByIdPessimistic(id)
                .map(ProductEntity::toModel);
    }
}
