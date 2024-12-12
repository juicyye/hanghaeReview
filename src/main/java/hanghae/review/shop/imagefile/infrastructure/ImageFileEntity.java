package hanghae.review.shop.imagefile.infrastructure;

import hanghae.review.shop.imagefile.domain.ImageFile;
import hanghae.review.shop.review.infrastructure.ReviewEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.checkerframework.common.value.qual.ArrayLen;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ImageFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String storeFileName;
    private String originalFileName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_entity_id")
    private ReviewEntity reviewEntity;

    public static ImageFileEntity fromEntity(ImageFile imageFile) {
        ImageFileEntity imageFileEntity = new ImageFileEntity();
        imageFileEntity.id = imageFile.getId();
        imageFileEntity.storeFileName = imageFile.getStoreFileName();
        imageFileEntity.originalFileName = imageFile.getOriginalFileName();
        imageFileEntity.reviewEntity = ReviewEntity.fromModel(imageFile.getReview());
        return imageFileEntity;
    }

    public ImageFile toEntity(){
        return ImageFile.builder()
                .id(id)
                .storeFileName(storeFileName)
                .originalFileName(originalFileName)
                .review(reviewEntity.toModel())
                .build();
    }
}
