package hanghae.review.shop.product.event;

import hanghae.review.shop.product.service.ProductMetricsService;
import hanghae.review.shop.product.service.lock.PessimisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ProductEventHandler {

    private final ProductMetricsService productMetricsService;
    private final PessimisticService pessimisticService;


    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateScore(ProductIncreaseEvent event) throws InterruptedException {
//        productMetricsService.updateReviewMetrics(event.productId(),event.score());
        pessimisticService.updatePessimistic(event.productId(), event.score());
//        optimisticFacade.executeOptimistic(event.productId(),event.score());
    }
}
