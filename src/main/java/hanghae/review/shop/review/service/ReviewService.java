package hanghae.review.shop.review.service;

import hanghae.review.shop.product.event.ProductIncreaseEvent;
import hanghae.review.shop.review.controller.req.ReviewCreateReqDto;
import hanghae.review.shop.review.domain.Review;
import hanghae.review.shop.review.service.port.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewRequestMapper reviewRequestMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void create(Long productId, ReviewCreateReqDto createReqDto, List<MultipartFile> files) {
        Review review = reviewRequestMapper.create(productId, createReqDto);
        reviewRepository.save(review);
        eventPublisher.publishEvent(new ProductIncreaseEvent(productId, createReqDto.score()));
    }

    public boolean isReviewWritten(Long userId, Long productId) {
        return reviewRepository.isReviewAlreadyWritten(userId, productId);
    }

}
