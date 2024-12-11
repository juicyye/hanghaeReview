package hanghae.review.shop.imagefile.infrastructure;

import hanghae.review.shop.review.infrastructure.ReviewEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class ImageFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String storeFileName;
    private String originalFileName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_entity_id")
    private ReviewEntity reviewEntity;
}
