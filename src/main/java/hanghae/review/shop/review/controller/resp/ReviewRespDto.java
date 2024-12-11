package hanghae.review.shop.review.controller.resp;

import java.time.LocalDateTime;

public record ReviewRespDto(
        Long id,
        Long userId,
        Float score,
        String content,
        String imageUrl,
        LocalDateTime createdAt
) {

    public ReviewRespDto(Long id, Long userId, Float score, String content, LocalDateTime createdAt) {
        this(id, userId, score, content, null, createdAt);
    }

    // imageUrl을 추가하는 메서드
    public static ReviewRespDto addImage(ReviewRespDto reviewRespDto, String imageUrl) {
        return new ReviewRespDto(
                reviewRespDto.id(),
                reviewRespDto.userId(),
                reviewRespDto.score(),
                reviewRespDto.content(),
                imageUrl,
                reviewRespDto.createdAt()
        );
    }
}
