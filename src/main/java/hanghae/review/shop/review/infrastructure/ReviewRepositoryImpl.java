package hanghae.review.shop.review.infrastructure;

import hanghae.review.shop.review.controller.resp.ProductReviewRespDto;
import hanghae.review.shop.review.domain.Review;
import hanghae.review.shop.review.service.port.ReviewRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository jpaRepository;
    private final ReviewDslRepositoryImpl reviewDslRepository;

    @Override
    public void save(Review review) {
        jpaRepository.save(ReviewEntity.fromModel(review));
    }

    @Override
    public List<Review> findAllByProduct(Long productId) {
        return jpaRepository.findAllByProductId(productId)
                .stream().map(ReviewEntity::toModel)
                .toList();
    }

    @Override
    public boolean isReviewAlreadyWritten(Long userId, Long productId) {
        Optional<Long> _review = jpaRepository.findReviewByUser(productId, userId);
        return _review.isPresent();
    }

    @Override
    public Optional<Review> findById(Long id) {
        return jpaRepository.findById(id)
                .map(ReviewEntity::toModel);
    }

    @Override
    public ProductReviewRespDto findProductReview(Long productId, Long cursor, int size) {
        return reviewDslRepository.findAllProductReviews(productId, cursor, size);
    }
}
