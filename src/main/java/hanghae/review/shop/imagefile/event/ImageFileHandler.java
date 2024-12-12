package hanghae.review.shop.imagefile.event;

import static hanghae.review.global.util.ReviewConst.REVIEW_IMAGE_PATH;

import hanghae.review.shop.imagefile.service.ImageFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageFileHandler {

    private final ImageFileService imageFileService;

    @EventListener
    public void handleReviewImageFileEvent(ReviewImageFileEvent event) {
        imageFileService.registerFile(event.file(), REVIEW_IMAGE_PATH, event.review());
    }
}
