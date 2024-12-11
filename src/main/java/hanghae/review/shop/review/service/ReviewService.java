package hanghae.review.shop.review.service;

import hanghae.review.shop.product.domain.Product;
import hanghae.review.shop.product.service.ProductService;
import hanghae.review.shop.product.service.port.ProductRepository;
import hanghae.review.shop.review.controller.req.ReviewCreateReqDto;
import hanghae.review.shop.review.service.port.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductService productService;

    @Transactional
    public void create(Long productId, ReviewCreateReqDto createReqDto) {
        Product product = productService.fetchProduct(productId);

    }

    public boolean canWriteReview(Long userId, Long productId) {
        return reviewRepository.isReviewAlreadyWritten(userId, productId);
    }
}
