package hanghae.review.shop.review.domain;

import hanghae.review.global.exception.CustomApiException;
import hanghae.review.global.util.ErrorMessage;
import hanghae.review.shop.product.domain.Product;
import java.time.LocalDateTime;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
    private Long id;
    private Long userId;
    private String content;
    private Product product;
    private Float score;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Review(Long userId, String content, Product product, Float score, LocalDateTime currentDate) {
        validate(userId, score, content);
        this.userId = userId;
        this.content = content;
        this.product = product;
        this.score = score;
        this.createdAt = currentDate;
        this.updatedAt = currentDate;
    }

    private void validate(Long userId, Float score, String content) {
        validateUser(userId);
        validateScoreRange(score);
        validateContentSize(content);
    }

    private void validateUser(Long userId) {
        if(userId == null) {
            throw new CustomApiException(ErrorMessage.USER_MUST_EXIST.getMessage());
        }
    }

    private void validateScoreRange(Float score) {
        if(score > 5 || score < 0) {
            throw new CustomApiException(ErrorMessage.INVALID_SCORE_RANGE.getMessage());
        }
    }

    private void validateContentSize(String content) {
        if (!Pattern.matches("^[ㄱ-ㅎ가-힣\\w\\s]{2,50}", content)) {
            throw new CustomApiException(ErrorMessage.INVALID_CONTENT_FORMAT.getMessage());
        }
    }
}
