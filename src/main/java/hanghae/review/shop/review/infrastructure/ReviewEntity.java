package hanghae.review.shop.review.infrastructure;

import hanghae.review.shop.product.infrastructure.ProductEntity;
import hanghae.review.shop.review.domain.Review;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String content;
    private Float score;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    public static ReviewEntity fromModel(Review review) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.id = review.getId();
        reviewEntity.userId = review.getUserId();
        reviewEntity.content = review.getContent();
        reviewEntity.score = review.getScore();
        reviewEntity.createdAt = review.getCreatedAt();
        reviewEntity.updatedAt = review.getUpdatedAt();
        reviewEntity.productEntity = ProductEntity.fromModel(review.getProduct());
        return reviewEntity;
    }

    public Review toModel() {
        return Review.builder()
                .id(id)
                .userId(userId)
                .content(content)
                .score(score)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
