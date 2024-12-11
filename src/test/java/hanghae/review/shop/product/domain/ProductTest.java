package hanghae.review.shop.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    @DisplayName("리뷰수와 총 점수로 평균 점수를 구할 수 있다")
    void updateReviewData() throws Exception {
        // given
        Product product = new Product();
        Long reviewCount = 3L;
        Float totalScore = 35f;

        // when
        product.updateReviewData(reviewCount, totalScore);

        // then
        assertAll(() -> {
            assertThat(product.getReviewCount()).isEqualTo(reviewCount);
            assertThat(product.getReviewCount()).isEqualTo(reviewCount);
            assertThat(product.getScore()).isEqualTo(totalScore / reviewCount);
        });
    }

}