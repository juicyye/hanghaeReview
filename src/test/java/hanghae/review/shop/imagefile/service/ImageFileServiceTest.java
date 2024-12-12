package hanghae.review.shop.imagefile.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import hanghae.review.global.exception.CustomApiException;
import hanghae.review.global.util.ErrorMessage;
import hanghae.review.mock.FakeImageFileRepository;
import hanghae.review.mock.FakeUuidRandomHolder;
import hanghae.review.shop.imagefile.domain.ImageFile;
import hanghae.review.shop.review.domain.Review;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

class ImageFileServiceTest {

    private ImageFileService imageFileService;
    private FakeImageFileRepository imageFileRepository;

    @BeforeEach
    void setUp() {
        imageFileRepository = new FakeImageFileRepository();
        FakeUuidRandomHolder randomHolder = new FakeUuidRandomHolder("랜덤");
        imageFileService = new ImageFileService(imageFileRepository, randomHolder);
    }

    @Test
    @DisplayName("리뷰에 대한 이미지 파일을 생성할 수 있다")
    void registerFile() throws Exception {
        // given
        String originalFileName = "내용.jpg";
        MultipartFile file = createFile(originalFileName, MediaType.IMAGE_PNG_VALUE);
        String directory = "경로";
        Review review = createReview();

        // when
        imageFileService.registerFile(file, directory, review);
        ImageFile result = imageFileRepository.findById(1L).get();

        // then
        assertAll(() -> {
            assertThat(result.getOriginalFileName()).isEqualTo(file.getOriginalFilename());
            assertThat(result.getStoreFileName()).isEqualTo("랜덤.jpg");
            assertThat(result.getReview()).isEqualTo(review);
        });
    }

    @Test
    @DisplayName("유효하지 않는 확장자를 입력하면 에러를 반환한다")
    void InvalidExtension() throws Exception {
        // given
        String fileName = "하이.123";
        MockMultipartFile file = createFile(fileName, MediaType.IMAGE_PNG_VALUE);

        // then
        assertThatThrownBy(() -> imageFileService.registerFile(file, fileName, null))
                .isInstanceOf(CustomApiException.class)
                .hasMessage(ErrorMessage.INVALID_IMAGE_FORMAT.getMessage());
    }

    private MockMultipartFile createFile(String originalFilename, String imageValue) {
        return new MockMultipartFile("파일", originalFilename,
                imageValue,
                "내용".getBytes(
                        StandardCharsets.UTF_8));
    }

    private Review createReview(){
        return Review.builder()
                .id(1L)
                .build();
    }

}