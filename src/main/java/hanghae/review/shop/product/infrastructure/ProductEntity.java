package hanghae.review.shop.product.infrastructure;

import hanghae.review.shop.product.domain.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long reviewCount;
    @Column(nullable = false)
    private Float score;

    public static ProductEntity fromModel(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.id = product.getId();
        productEntity.reviewCount = product.getReviewCount();
        productEntity.score = product.getTotalScore();
        return productEntity;
    }

    public Product toModel(){
        return Product.builder()
                .id(id)
                .reviewCount(reviewCount)
                .totalScore(score)
                .build();
    }

}
