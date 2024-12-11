package hanghae.review.global.controller.resp;

public record ResponseDto<T>(
        int code,
        String message,
        T data
) {
}
