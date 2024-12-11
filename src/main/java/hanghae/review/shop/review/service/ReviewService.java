package hanghae.review.shop.review.service;

import hanghae.review.shop.product.domain.Product;
import hanghae.review.shop.product.event.ProductIncreaseEvent;
import hanghae.review.shop.review.controller.req.ReviewCreateReqDto;
import hanghae.review.shop.review.domain.Review;
import hanghae.review.shop.review.domain.ReviewUpdate;
import hanghae.review.shop.review.service.port.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewRequestMapper reviewRequestMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void create(Long productId, ReviewCreateReqDto createReqDto) {
        Review review = reviewRequestMapper.create(productId, createReqDto);
        reviewRepository.save(review);
        eventPublisher.publishEvent(new ProductIncreaseEvent(productId, createReqDto.score()));
    }

    public boolean canWriteReview(Long userId, Long productId) {
        return reviewRepository.isReviewAlreadyWritten(userId, productId);
    }

    // todo 여기서 VO 객체를 반환해도 되는가?
    public ReviewUpdate getTotalReviewScore(Long productId) {
        List<Review> reviews = reviewRepository.findAllByProduct(productId);
        long count = reviews.size();
        float totalScore = (float) reviews.stream()
                .mapToDouble(Review::getScore)
                .sum();
        return new ReviewUpdate(count, totalScore);
    }
}
