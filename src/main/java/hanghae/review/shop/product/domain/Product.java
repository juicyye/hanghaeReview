package hanghae.review.shop.product.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long id;
    private Long reviewCount;
    private Float score;

    public void updateReviewData(Long reviewCount, Float totalScore) {
        this.reviewCount = reviewCount;
        this.score = calculateScore(totalScore);
    }

    private Float calculateScore(Float totalScore) {
        return totalScore / reviewCount;
    }

}
