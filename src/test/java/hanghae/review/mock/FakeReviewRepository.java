package hanghae.review.mock;

import hanghae.review.shop.review.controller.resp.ReviewRespDto;
import hanghae.review.shop.review.domain.Review;
import hanghae.review.shop.review.service.port.ReviewRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeReviewRepository implements ReviewRepository {
    private List<Review> data = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();

    @Override
    public Review save(Review review) {
        if (review.getId() == null || review.getId() == 0) {
            Review newReview = Review.builder()
                    .id(counter.getAndIncrement())
                    .userId(review.getUserId())
                    .content(review.getContent())
                    .product(review.getProduct())
                    .score(review.getScore())
                    .createdAt(review.getCreatedAt())
                    .updatedAt(review.getUpdatedAt())
                    .build();
            data.add(newReview);
            return newReview;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), review.getId()));
            data.add(review);
            return review;
        }
    }

    @Override
    public boolean isReviewAlreadyWritten(Long userId, Long productId) {
        return data.stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .anyMatch(i -> i.getUserId().equals(userId));
    }

    @Override
    public Optional<Review> findById(Long id) {
        return data.stream()
                .filter(i -> Objects.equals(i.getId(), id))
                .findFirst();
    }

    @Override
    public List<Review> findProductReview(Long productId, Long cursor, int size) {
        return null;
    }
}
