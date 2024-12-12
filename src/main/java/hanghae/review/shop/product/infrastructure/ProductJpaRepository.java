package hanghae.review.shop.product.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    @Modifying
    @Query("update ProductEntity pe set pe.reviewCount = :reviewCount, pe.score = :score where pe.id = :id")
    void updateProductScore(@Param("reviewCount") Long reviewCount, @Param("score") Float score, @Param("id") Long id);

}
