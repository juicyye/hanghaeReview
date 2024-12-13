package hanghae.review.shop.product.service.lock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptimisticFacade {

    private final OptimisticService optimisticService;

    public void executeOptimistic(Long productId, Float score) throws InterruptedException {
        while (true) {
            try {
                optimisticService.updateOptimistic(productId, score);
                break;
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }
    }
}
