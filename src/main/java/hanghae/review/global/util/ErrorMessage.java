package hanghae.review.global.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    NOT_FOUND_PRODUCT("요청하신 상품번호를 찾을 수 없습니다."),
    DUPLICATE_REVIEW_WRITTEN("이미 작성하신 상품에는 다시 작성하실 수 없습니다."),
    INVALID_IMAGE_FORMAT("유효하지 않는 이미지 파일 확장자입니다.")
    ;

    private final String message;


}
