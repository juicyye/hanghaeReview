package hanghae.review.global.util;

import static hanghae.review.global.util.ReviewConst.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    NOT_FOUND_PRODUCT("요청하신 상품번호를 찾을 수 없습니다."),
    DUPLICATE_REVIEW_WRITTEN("이미 작성하신 상품에는 다시 작성하실 수 없습니다."),
    INVALID_IMAGE_FORMAT("유효하지 않는 이미지 파일 확장자입니다."),
    USER_MUST_EXIST("유저아이디는 필수입니다."),
    INVALID_SCORE_RANGE(String.format("점수는 %d ~ %d 사이로 입력해주세요", START_SCORE_RANGE, END_SCORE_RANGE)),
    INVALID_CONTENT_FORMAT(String.format("내용은 %d ~ %d 사이로 입력해주세요", START_CONTENT_SIZE, END_CONTENT_SIZE))
    ;

    private final String message;


}
