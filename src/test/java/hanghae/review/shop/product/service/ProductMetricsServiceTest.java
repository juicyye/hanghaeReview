package hanghae.review.shop.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import hanghae.review.mock.FakeProductRepository;
import hanghae.review.shop.product.domain.Product;
import hanghae.review.shop.review.domain.Review;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductMetricsServiceTest {

    private ProductMetricsService productMetricsService;
    private FakeProductRepository productRepository;

    Product product = createProduct(2, 3L);

    @BeforeEach
    void setUp() {
        productRepository = new FakeProductRepository();
        productMetricsService = new ProductMetricsService(productRepository);

        productRepository.save(product);
    }

    @Test
    @DisplayName("리뷰의 개수와 평균 점수를 알 수 있다")
    void updateReviewMetrics() throws Exception {
        // given
        Long productId = 1L;
        Integer score = 2;

        // when
        productMetricsService.updateReviewMetrics(productId,score);
        Product result = productRepository.findById(productId).get();

        // then
        assertAll(() -> {
            assertThat(result.getTotalScore()).isEqualTo(4);
            assertThat(result.getReviewCount()).isEqualTo(4);
        });
    }

    private Product createProduct(float score, long reviewCount) {
        return Product.builder()
                .id(1L)
                .totalScore(score)
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