package hanghae.review.shop.imagefile.service;

import static hanghae.review.global.util.ReviewConst.*;

import hanghae.review.global.exception.CustomApiException;
import hanghae.review.global.serivce.port.UUIDRandomHolder;
import hanghae.review.global.util.ErrorMessage;
import hanghae.review.shop.imagefile.domain.ImageFile;
import hanghae.review.shop.imagefile.service.port.ImageFileRepository;
import hanghae.review.shop.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageFileService {

    private final ImageFileRepository imageFileRepository;
    private final UUIDRandomHolder uuidRandomHolder;

    @Transactional
    public void registerFile(MultipartFile file, String directory, Review review) {
        if(file == null) return;
        String originalFilename = file.getOriginalFilename();
        String storeFileName = generateStoreFileName(originalFilename);

        ImageFile imageFile = new ImageFile(storeFileName, originalFilename, review);
        imageFileRepository.save(imageFile);
    }

    private String generateStoreFileName(String originalFilename) {
        int pos = originalFilename.lastIndexOf(IMAGE_FILE_DELIMITER);
        String extension = originalFilename.substring(pos + 1);

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new CustomApiException(ErrorMessage.INVALID_IMAGE_FORMAT.getMessage());
        }

        // todo .webp 파일로 바꾸기
        String randomFileName = uuidRandomHolder.getRandom();
        return randomFileName + IMAGE_FILE_DELIMITER + extension;
    }
}
