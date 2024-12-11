package hanghae.review.shop.review.controller.req;

public record ReviewCreateReqDto(
        Long userId,
        Double score,
        String content
) {

}
