package hanghae.review.shop.review.controller.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewCreateReqDto(
        @NotNull
        Long userId,
        @NotNull
        Float score,
        @NotBlank
        String content
) {

}
