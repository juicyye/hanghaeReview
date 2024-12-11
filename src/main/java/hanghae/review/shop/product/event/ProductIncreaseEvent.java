package hanghae.review.shop.product.event;

import hanghae.review.shop.product.domain.Product;

public record ProductIncreaseEvent(
        Long productId,
        Float score

) {
}
