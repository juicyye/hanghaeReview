package hanghae.review.mock;

import hanghae.review.shop.imagefile.domain.ImageFile;
import hanghae.review.shop.imagefile.service.port.ImageFileRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeImageFileRepository implements ImageFileRepository {
    private List<ImageFile> data = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();

    @Override
    public void save(ImageFile imageFile) {
        if (imageFile.getId() == null || imageFile.getId().equals(0L)) {
            ImageFile newImageFile = ImageFile.builder().id(counter.incrementAndGet())
                    .originalFileName(imageFile.getOriginalFileName()).storeFileName(imageFile.getStoreFileName())
                    .review(imageFile.getReview()).build();
            data.add(newImageFile);
        } else {
            data.removeIf(i -> i.getId().equals(imageFile.getId()));
            data.add(imageFile);
        }
    }

    @Override
    public Optional<ImageFile> findById(Long id) {
        return data.stream().filter(i -> i.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<String> findByReviewId(Long reviewId) {
        return Optional.empty();
    }
}
