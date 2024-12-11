package hanghae.review.shop.review.controller.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

public record ReviewCreateReqDto(
        @NotNull(message = "유저 아이디를 입력해주세요")
        Long userId,
        @Range(min = 0, max = 5, message = "점수는 0~5사이로만 입력하실 수 있습니다")
        @NotNull(message = "평가 점수는 필수입니다.")
        Float score,
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣\\w\\s]{2,50}", message = "내용은 2~50자로 입력해주세요")
        String content
) {

}
