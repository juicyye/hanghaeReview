package hanghae.review.shop.review.service;

import hanghae.review.global.exception.CustomApiException;
import hanghae.review.global.util.ErrorMessage;
import hanghae.review.shop.imagefile.event.ReviewImageFileEvent;
import hanghae.review.shop.product.event.ProductIncreaseEvent;
import hanghae.review.shop.review.controller.req.ReviewCreateReqDto;
import hanghae.review.shop.review.controller.resp.ReviewRespDto;
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
    public void create(Long productId, ReviewCreateReqDto createReqDto, MultipartFile file) {
        isReviewWritten(createReqDto.userId(), productId);
        Review review = reviewRequestMapper.create(productId, createReqDto);
        reviewRepository.save(review);

        eventPublisher.publishEvent(new ProductIncreaseEvent(productId, createReqDto.score()));
        eventPublisher.publishEvent(new ReviewImageFileEvent(file, review));
    }

    private void isReviewWritten(Long userId, Long productId) {
        boolean result = reviewRepository.isReviewAlreadyWritten(userId, productId);
        if (result) {
            throw new CustomApiException(ErrorMessage.DUPLICATE_REVIEW_WRITTEN.getMessage());
        }
    }

    public List<ReviewRespDto> fetchProductReviews(Long productId, Long cursor, int size) {
        return reviewRepository.findProductReview(productId, cursor, size);
    }
}
