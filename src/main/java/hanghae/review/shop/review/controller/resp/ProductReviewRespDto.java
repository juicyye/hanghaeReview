package hanghae.review.shop.review.controller.resp;

import hanghae.review.shop.product.domain.Product;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProductReviewRespDto {

    private Long totalCount;
    private int score;
    private Long cursor;
    private List<ReviewRespDto> reviews = new ArrayList<>();

    public static ProductReviewRespDto of(Product product, List<ReviewRespDto> reviews, Long cursor) {
        return ProductReviewRespDto.builder()
                .totalCount(product.getReviewCount())
                .score(product.calculateAverageScore())
                .cursor(cursor)
                .reviews(reviews)
                .build();
    }
}
