package hanghae.review.shop.review.controller.req;

public record ReviewCreateReqDto(
        Long userId,
        Integer score,
        String content

) {

}
