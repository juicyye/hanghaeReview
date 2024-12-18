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

    /**
     * 도메인 생성 전용 클래스
     */
    public Review create(Long productId, ReviewCreateReqDto reqDto) {
        Product product = productService.fetchProduct(productId);
        return new Review(reqDto.userId(), reqDto.content(), product, reqDto.score(), timeRandomHolder.getCurrentTime());
    }
}
