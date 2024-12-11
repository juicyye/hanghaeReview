package hanghae.review.shop.product.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductEventHandler {

    // todo 트랜잭션 이벤트처리 및 Async
    @EventListener
    public void updateScore(ProductIncreaseEvent event) {

    }
}
