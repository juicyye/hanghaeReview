package hanghae.review.shop.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import hanghae.review.global.exception.CustomApiException;
import hanghae.review.global.util.ErrorMessage;
import hanghae.review.mock.FakeImageFileRepository;
import hanghae.review.mock.FakeProductRepository;
import hanghae.review.mock.FakeReviewRepository;
import hanghae.review.mock.FakeTimeRandomHolder;
import hanghae.review.shop.imagefile.service.ImageFileService;
import hanghae.review.shop.product.domain.Product;
import hanghae.review.shop.product.event.ProductIncreaseEvent;
import hanghae.review.shop.product.service.ProductService;
import hanghae.review.shop.review.controller.req.ReviewCreateReqDto;
import hanghae.review.shop.review.domain.Review;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;

class ReviewServiceTest {

    private ReviewService reviewService;
    private LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 11, 23, 11);
    private ApplicationEventPublisher eventPublisher;
    private FakeReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        ImageFileService imageFileService = Mockito.mock(ImageFileService.class);
        FakeProductRepository productRepository = new FakeProductRepository();
        reviewRepository = new FakeReviewRepository();
        ProductService productService = new ProductService(productRepository);
        FakeTimeRandomHolder timeRandomHolder = new FakeTimeRandomHolder(localDateTime);
        ReviewRequestMapper reviewRequestMapper = new ReviewRequestMapper(productService, timeRandomHolder);

        eventPublisher = Mockito.mock(ApplicationEventPublisher.class);
        reviewService = new ReviewService(reviewRepository, reviewRequestMapper, eventPublisher,imageFileService);
        Product product = createProduct(2, 2L);
        productRepository.save(product);
    }

    @Test
    @DisplayName("올바른 값을 입력하면 리뷰를 생성할 수 있다")
    void createReview() throws Exception {
        // given
        Long productId = 0L;
        ReviewCreateReqDto request = new ReviewCreateReqDto(1L, 2, "하이요");

        // when
        reviewService.create(productId, request, null);
        Review review = reviewRepository.findById(0L).get();

        // then
        assertAll(() -> {
            assertThat(review.getId()).isEqualTo(0L);
            assertThat(review.getContent()).isEqualTo("하이요");
            assertThat(review.getCreatedAt()).isEqualTo(localDateTime);
            Mockito.verify(eventPublisher).publishEvent(Mockito.any(ProductIncreaseEvent.class));
        });
    }

    @Nested
    @DisplayName("한 유저는 각 상품에 대해 하나의 리뷰만 작성할 수 있다")
    class OneUserOneReview {
        Long productId = 0L;
        Long userId = 0L;

        @Test
        @DisplayName("유저가 상품에 리뷰를 쓰지 않았다면 리뷰를 쓸 수 있다")
        void canWriteReview() throws Exception {
            // given
            reviewService.create(productId, new ReviewCreateReqDto(userId, 2, "내용"), null);

            // when
            Review review = reviewRepository.findById(0L).get();

            // then
            assertThat(review.getScore()).isEqualTo(2);
        }

        @Test
        @DisplayName("유저가 이미 상품에 리뷰를 썼다면 리뷰를 쓸 수 없다")
        void cantWriteReview() throws Exception {
            // given
            reviewService.create(productId, new ReviewCreateReqDto(userId, 2, "내용"), null);

            // then
            assertThatThrownBy(() -> reviewService.create(productId, new ReviewCreateReqDto(userId, 2, "내용"), null))
                    .isInstanceOf(CustomApiException.class)
                    .hasMessage(ErrorMessage.DUPLICATE_REVIEW_WRITTEN.getMessage());
        }
    }

    private Product createProduct(float score, long reviewCount) {
        return Product.builder()
                .totalScore(score)
                .reviewCount(reviewCount)
                .build();
    }

}