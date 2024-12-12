package hanghae.review.shop.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import hanghae.review.mock.FakeProductRepository;
import hanghae.review.mock.FakeReviewRepository;
import hanghae.review.shop.product.domain.Product;
import hanghae.review.shop.review.domain.Review;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductMetricsServiceTest {

    private ProductMetricsService productMetricsService;
    private FakeProductRepository productRepository;
    private FakeReviewRepository reviewRepository;
    Product product = createProduct(2.2f, 3L);

    @BeforeEach
    void setUp() {
        productRepository = new FakeProductRepository();
        reviewRepository = new FakeReviewRepository();
        productMetricsService = new ProductMetricsService(productRepository, reviewRepository);

        productRepository.save(product);
        reviewRepository.save(createReview(product, 3.5f, 1L));
        reviewRepository.save(createReview(product, 3.2f, 1L));
    }

    @Test
    @DisplayName("리뷰의 개수와 평균 점수를 알 수 있다")
    void updateReviewMetrics() throws Exception {
        // given
        Long productId = 1L;

        // when
        productMetricsService.updateReviewMetrics(productId);
        Product result = productRepository.findById(productId).get();

        // then
        assertAll(() -> {
            assertThat(result.getScore()).isEqualTo((3.2f + 3.5f) / 2);
            assertThat(result.getReviewCount()).isEqualTo(2);
        });
    }

    private Product createProduct(float score, long reviewCount) {
        return Product.builder()
                .id(1L)
                .score(score)
                .reviewCount(reviewCount)
                .build();
    }

    private Review createReview(Product product, float score, long userId) {
        return Review.builder()
                .content("내용")
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .score(score)
                .product(product)
                .build();
    }

}