package hanghae.review.shop.product.infrastructure;

import hanghae.review.shop.product.domain.Product;
import hanghae.review.shop.review.infrastructure.ReviewEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long reviewCount;
    @Column(nullable = false)
    private Double score;

    public static ProductEntity fromModel(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.reviewCount = product.getReviewCount();
        productEntity.score = product.getScore();
        return productEntity;
    }

    public Product toModel(){
        return Product.builder()
                .id(id)
                .reviewCount(reviewCount)
                .score(score)
                .build();
    }

}
