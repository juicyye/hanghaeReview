package hanghae.review.shop.review.service;

import hanghae.review.global.serivce.port.TimeRandomHolder;
import hanghae.review.shop.product.domain.Product;
import hanghae.review.shop.product.service.ProductService;
import hanghae.review.shop.review.controller.req.ReviewCreateReqDto;
import hanghae.review.shop.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReviewRequestMapper {

    private final ProductService productService;
    private final TimeRandomHolder timeRandomHolder;

    public Review create(Long productId, ReviewCreateReqDto createReqDto) {
        Product product = productService.fetchProduct(productId);
        return Review.builder()
                .content(createReqDto.content())
                .userId(createReqDto.userId())
                .score(createReqDto.score())
                .product(product)
                .createdAt(timeRandomHolder.getCurrentTime())
                .updatedAt(timeRandomHolder.getCurrentTime())
                .build();
    }
}
