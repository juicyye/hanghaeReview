package hanghae.review.shop.review.controller.resp;

import hanghae.review.shop.review.domain.Review;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRespDto{

    private Long id;
    private Long userId;
    private Integer score;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;

    public ReviewRespDto(Review review) {
        this.id = review.getId();
        this.userId = review.getUserId();
        this.score = review.getScore().intValue();
        this.content = review.getContent();
        this.imageUrl = review.getImageUrl();
        this.createdAt = review.getCreatedAt();
    }
}
