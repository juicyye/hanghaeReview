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

    /**
     * 총 점수와 리뷰 수를 통해 평균 점수를 반환한다
     */
    public int calculateAverageScore() {
        return Math.round(totalScore / reviewCount);
    }

}
