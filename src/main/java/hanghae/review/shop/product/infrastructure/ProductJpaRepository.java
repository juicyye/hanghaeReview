package hanghae.review.shop.product.infrastructure;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select pe from ProductEntity pe where pe.id = :id")
    Optional<ProductEntity> fetchProductByIdOptimistic(@Param("id") Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select pe from ProductEntity pe where pe.id = :id")
    Optional<ProductEntity> fetchProductByIdPessimistic(@Param("id") Long id);

}
