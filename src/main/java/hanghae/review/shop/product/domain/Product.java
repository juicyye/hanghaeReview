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
    private Float totalScore;

    public void updateReviewData(Float score) {
        this.reviewCount += 1;
        this.totalScore += score;
    }

    public int calculateAverageScore() {
        return Math.round(totalScore / reviewCount);
    }

}
