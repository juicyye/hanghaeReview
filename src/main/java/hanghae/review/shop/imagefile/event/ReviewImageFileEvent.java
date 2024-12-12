package hanghae.review.shop.imagefile.event;

import hanghae.review.shop.review.domain.Review;
import org.springframework.web.multipart.MultipartFile;

public record ReviewImageFileEvent(
        MultipartFile file,
        Review review
) {
}
