package hanghae.review.shop.product.event;

public record ProductIncreaseEvent(
        Long productId,
        Integer score

) {
}
