package hanghae.review.global.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    NOT_FOUND_PRODUCT(-1, "요청하신 상품번호를 찾을 수 없습니다.");

    private final int code;
    private final String message;


}
