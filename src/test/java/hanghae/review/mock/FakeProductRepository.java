package hanghae.review.mock;

import hanghae.review.shop.product.domain.Product;
import hanghae.review.shop.product.service.port.ProductRepository;
import hanghae.review.shop.review.domain.Review;
import hanghae.review.shop.review.service.port.ReviewRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeProductRepository implements ProductRepository {
    private List<Product> data = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();

    @Override
    public void save(Product product) {
        if(product.getId() == null || product.getId() == 0) {
            Product newProduct = Product.builder()
                    .id(counter.getAndIncrement())
                    .reviewCount(product.getReviewCount())
                    .score(product.getScore())
                    .build();
            data.add(newProduct);
        } else{
            data.removeIf(item -> Objects.equals(item.getId(), product.getId()));
            data.add(product);
        }
    }

    @Override
    public Optional<Product> findById(Long id) {
        return data.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        data.removeIf(item -> Objects.equals(item.getId(), id));
    }

    @Override
    public void modifyProductReviewStats(Long reviewCount, Float score, Long productId) {
    }
}
