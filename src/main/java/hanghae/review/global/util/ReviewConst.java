package hanghae.review.global.util;

import java.util.List;

public class ReviewConst {

    public static final String VALIDATE_FAIL = "유효성 검사 실패";
    public static final String IMAGE_FILE_DELIMITER = ".";
    public static final List<String> ALLOWED_EXTENSIONS = List.of("jpg", "png", "gif", "jpeg","webp");
    public static final String REVIEW_IMAGE_PATH = "review";
    public static final int START_SCORE_RANGE = 0;
    public static final int END_SCORE_RANGE = 5;
    public static final int START_CONTENT_SIZE = 2;
    public static final int END_CONTENT_SIZE = 50;
}
