package hanghae.review.shop.review.domain;

import hanghae.review.shop.product.domain.Product;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
    private Long id;
    private Long userId;
    private String content;
    private Product product;
    private Float score;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
