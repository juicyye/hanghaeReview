package hanghae.review.shop.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import hanghae.review.IntegrationTestSupport;
import hanghae.review.mock.FakeProductRepository;
import hanghae.review.mock.FakeTimeRandomHolder;
import hanghae.review.shop.product.domain.Product;
import hanghae.review.shop.product.service.ProductService;
import hanghae.review.shop.review.controller.req.ReviewCreateReqDto;
import hanghae.review.shop.review.domain.Review;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class ReviewRequestMapperTest extends IntegrationTestSupport {

    private ReviewRequestMapper reviewRequestMapper;
    private final LocalDateTime value = LocalDateTime.of(2024, 12, 11, 14, 14, 10);

    @BeforeEach
    void setUp() {
        FakeProductRepository productRepository = new FakeProductRepository();
        ProductService productService = new ProductService(productRepository);
        FakeTimeRandomHolder randomHolder = new FakeTimeRandomHolder(value);
        reviewRequestMapper = new ReviewRequestMapper(productService, randomHolder);
        productRepository.save(createProduct(1.5f, 2L));
    }

    @Test
    @DisplayName("올바른 값을 입력하면 review를 생성할 수 있다")
    void createReview() throws Exception {
        // given
        Long userId = 1L;
        float score = 4.0f;
        String content = "좋아요";
        ReviewCreateReqDto request = new ReviewCreateReqDto(userId, score, content);

        // when
        Review result = reviewRequestMapper.create(0L, request);

        // then
        assertAll(() -> {
            assertThat(result.getUserId()).isEqualTo(userId);
            assertThat(result.getContent()).isEqualTo(content);
            assertThat(result.getScore()).isEqualTo(score);
            assertThat(result.getCreatedAt()).isEqualTo(value);
            assertThat(result.getProduct()).isNotNull()
                    .extracting(Product::getReviewCount)
                    .isEqualTo(2L);

        });
    }

    private Product createProduct(float score, long reviewCount) {
        return Product.builder()
                .totalScore(score)
                .reviewCount(reviewCount)
                .build();
    }

}