package hanghae.review.shop.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import hanghae.review.IntegrationTestSupport;
import hanghae.review.shop.product.infrastructure.ProductEntity;
import hanghae.review.shop.product.infrastructure.ProductJpaRepository;
import hanghae.review.shop.review.controller.req.ReviewCreateReqDto;
import hanghae.review.shop.review.service.ReviewService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ConcurrencyIssueTest extends IntegrationTestSupport {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductJpaRepository productRepository;

    @BeforeEach
    void setUp() {
        ProductEntity productEntity = createProductEntity(2.2f, 1L);
        productRepository.save(productEntity);
    }

    @Test
    @DisplayName("동시성 문제 체크")
    void 동시에_100개의_요청() throws Exception {
        // given
        long startTime = System.currentTimeMillis();
        int threadCount = 200;
        ExecutorService es = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            es.submit(() -> {
                try {
                    long l = Integer.valueOf(finalI).longValue();
                    ReviewCreateReqDto request = new ReviewCreateReqDto(l, 2.2f, "내용");
                    reviewService.create(1L, request, null);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        // when
        ProductEntity productEntity = productRepository.findById(1L).get();
        long endTime = System.currentTimeMillis();
        System.out.printf("소요 시간 : %dms", endTime - startTime);

        // then
        assertAll(() -> {
            assertThat(productEntity.getScore()).isEqualTo(2.2f);
            assertThat(productEntity.getReviewCount()).isEqualTo(threadCount);
        });
    }

    private ProductEntity createProductEntity(float score, long reviewCount) {
        return ProductEntity.builder()
                .score(score)
                .reviewCount(reviewCount)
                .build();
    }
}
