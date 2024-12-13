package hanghae.review.shop.review.infrastructure;

import hanghae.review.shop.product.infrastructure.ProductEntity;
import hanghae.review.shop.review.domain.Review;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private Float score;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
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
                .product(productEntity.toModel())
                .build();
    }
}
