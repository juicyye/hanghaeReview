package hanghae.review.shop.review.service;

import hanghae.review.global.exception.CustomApiException;
import hanghae.review.global.util.ErrorMessage;
import hanghae.review.shop.imagefile.event.ReviewImageFileEvent;
import hanghae.review.shop.imagefile.service.port.ImageFileRepository;
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
    private final ImageFileRepository imageFileRepository;

    /**
     * 리뷰 생성
     * 리뷰를 생성하면 이미지파일과 상품 리뷰증가 이벤트가 발행된다
     */
    @Transactional
    public void create(Long productId, ReviewCreateReqDto createReqDto, MultipartFile file) {
        isReviewWritten(createReqDto.userId(), productId);
        Review review = reviewRequestMapper.create(productId, createReqDto);
        Review savedReview = reviewRepository.save(review);

        eventPublisher.publishEvent(new ProductIncreaseEvent(productId, createReqDto.score()));
        eventPublisher.publishEvent(new ReviewImageFileEvent(file, savedReview));
    }

    /**
     * 유저가 하나의 상품에 이미 리뷰를 작성했는지 확인
     */
    private void isReviewWritten(Long userId, Long productId) {
        boolean result = reviewRepository.isReviewAlreadyWritten(userId, productId);
        if (result) {
            throw new CustomApiException(ErrorMessage.DUPLICATE_REVIEW_WRITTEN.getMessage());
        }
    }

    /**
     * 상품에 대한 리뷰를 커서 페이지네이션으로 가져온다
     * 상품에 대한 리뷰와 리뷰에 대한 이미지파일을 불어오는 역할을 한다
     */
    public List<Review> fetchProductReviews(Long productId, Long cursor, int size) {

        List<Review> reviews = reviewRepository.findProductReview(productId, cursor, size);
        for (Review review : reviews) {
            String imageFileUrl = imageFileRepository.findByReviewId(review.getId()).orElse(null);
            review.setImageAddress(imageFileUrl);
        }
        return reviews;
    }
}
