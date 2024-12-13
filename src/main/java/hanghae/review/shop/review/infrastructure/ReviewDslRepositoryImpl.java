package hanghae.review.shop.review.infrastructure;

import static hanghae.review.shop.imagefile.infrastructure.QImageFileEntity.imageFileEntity;
import static hanghae.review.shop.product.infrastructure.QProductEntity.productEntity;
import static hanghae.review.shop.review.infrastructure.QReviewEntity.reviewEntity;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hanghae.review.shop.review.controller.resp.ProductReviewRespDto;
import hanghae.review.shop.review.controller.resp.ReviewRespDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReviewDslRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    /**
     * 상품에 대한 리뷰를 DB로부터 읽어온다
     */
    public List<ReviewEntity> findAllProductReviews(Long productId, Long cursor, int size) {
        return queryFactory.select(reviewEntity)
                .from(reviewEntity)
                .join(reviewEntity.productEntity, productEntity)
                .where(productEntity.id.eq(productId))
                .where(cursor == 0L ? null : reviewEntity.id.gt(cursor))
                .limit(size)
                .orderBy(reviewEntity.createdAt.desc())
                .fetch();
    }
}
