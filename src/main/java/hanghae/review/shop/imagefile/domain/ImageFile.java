package hanghae.review.shop.imagefile.domain;

import hanghae.review.shop.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ImageFile {

    private Long id;
    private String storeFileName;
    private String originalFileName;
    private Review review;

    public ImageFile(String storeFileName, String originalFileName, Review review) {
        this.storeFileName = storeFileName;
        this.originalFileName = originalFileName;
        this.review = review;
    }
}
