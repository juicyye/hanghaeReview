package hanghae.review.shop.review.service;

import hanghae.review.global.serivce.port.TimeRandomHolder;
import hanghae.review.shop.product.domain.Product;
import hanghae.review.shop.product.service.ProductService;
import hanghae.review.shop.review.controller.req.ReviewCreateReqDto;
import hanghae.review.shop.review.domain.Review;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewRequestMapper {

    private ProductService productService;
    private final TimeRandomHolder timeRandomHolder;

    public Review create(Long productId, ReviewCreateReqDto createReqDto) {
        Product product = productService.fetchProduct(productId);
        return Review.builder()
                .content(createReqDto.content())
                .userId(createReqDto.userId())
                .product(product)
                .createdAt(timeRandomHolder.getCurrentTime())
                .updatedAt(timeRandomHolder.getCurrentTime())
                .build();
    }
}
