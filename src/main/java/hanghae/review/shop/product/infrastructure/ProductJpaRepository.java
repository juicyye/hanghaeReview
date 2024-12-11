package hanghae.review.shop.product.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    @Query("update ProductEntity pe set pe.reviewCount = :viewCount, pe.score = :score where pe.id = :id")
    void updateProductScore(@Param("viewCount") Long viewCount, @Param("score") Float score, @Param("id") Long id);
}
