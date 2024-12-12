package hanghae.review.dummy;

import hanghae.review.shop.product.domain.Product;
import hanghae.review.shop.product.service.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("prod")
public class Init {

    private final ProductService productService;

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            productService.create(createProduct());
        }
    }

    private Product createProduct(){
        return Product.builder()
                .score(0.0f)
                .reviewCount(0L)
                .build();
    }
}
