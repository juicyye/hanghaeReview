package hanghae.review.shop.product.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import hanghae.review.shop.product.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductEntityTest {

    @Test
    @DisplayName("product 도메인으로 ProductEntity를 생성할 수 있다")
    void createProductEntity() throws Exception {
        // given
        float score = 1.1f;
        long count = 3L;
        Product product = createProduct(score, count);

        // when
        ProductEntity result = ProductEntity.fromModel(product);

        // then
        assertAll(() -> {
            assertThat(result.getScore()).isEqualTo(score);
            assertThat(result.getReviewCount()).isEqualTo(count);
        });
    }

    private Product createProduct(float score, long count){
        return Product.builder()
                .reviewCount(count)
                .totalScore(score)
                .build();
    }

}