package hanghae.review.shop.product.event;

import hanghae.review.shop.product.service.OptimisticFacade;
import hanghae.review.shop.product.service.PessimisticService;
import hanghae.review.shop.product.service.ProductMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ProductEventHandler {

    private final ProductMetricsService productMetricsService;
    private final OptimisticFacade optimisticFacade;
    private final PessimisticService pessimisticService;


    @TransactionalEventListener
    @Async("customTaskExecutor")
    public void updateScore(ProductIncreaseEvent event) throws InterruptedException {
        productMetricsService.updateReviewMetrics(event.productId());
//        pessimisticService.updatePessimistic(event.productId());
//        optimisticFacade.updateOptimistic(event.productId());
    }
}
