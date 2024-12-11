package hanghae.review.shop.review.controller.req;

public record ReviewCreateReqDto(
        Long userId,
        Float score,
        String content
) {

}
