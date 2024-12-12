package hanghae.review.shop.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptimisticFacade {

    private final OptimisticService optimisticService;

    public void updateOptimistic(Long productId) throws InterruptedException {
        while(true){
            try{
                optimisticService.updateOptimistic(productId);
                break;
            } catch (Exception e){
                Thread.sleep(50);
            }
        }
    }
}
