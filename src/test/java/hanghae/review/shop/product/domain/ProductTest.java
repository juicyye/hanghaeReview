package hanghae.review.shop.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    @DisplayName("상품에는 총 리뷰수와 총 평점이 있다")
    void createProduct_Test() throws Exception {
        // given
        long reviewCount = 2L;
        int score = 5;
        Product product = createProduct(1L, reviewCount, score);

        // when
        product.updateReviewData(score);

        // then
        assertAll(() -> {
            assertThat(product.getReviewCount()).isEqualTo(reviewCount + 1);
            assertThat(product.getTotalScore()).isEqualTo(score * 2);
        });
    }

    @Test
    @DisplayName("상품 총 리뷰수와 총 평점으로 평균 점수를 구할 수 있다")
    void canCalculateAverage() throws Exception {
        // given
        long reviewCount = 2L;
        int score = 5;
        Product product = createProduct(1L, reviewCount, score);

        // when
        int result = product.calculateAverageScore();

        // then
        assertThat(result).isEqualTo(Math.round((float) score / reviewCount));
    }

    private Product createProduct(long id, long reviewCount, float totalScore){
        return Product.builder()
                .id(id)
                .reviewCount(reviewCount)
                .totalScore(totalScore)
                .build();
    }

}