package hanghae.review.shop.review.infrastructure;

import static hanghae.review.shop.imagefile.infrastructure.QImageFileEntity.imageFileEntity;
import static hanghae.review.shop.product.infrastructure.QProductEntity.productEntity;
import static hanghae.review.shop.review.infrastructure.QReviewEntity.reviewEntity;

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

    public List<ReviewRespDto> findAllProductReviews(Long productId, Long cursor, int size) {
        List<ReviewRespDto> reviewRespDtos = getProductReviews(productId, cursor, size);
        setReviewImage(reviewRespDtos);

        return reviewRespDtos;
    }

    private List<ReviewRespDto> getProductReviews(Long productId, Long cursor, int size) {
        return queryFactory.select(
                        Projections.fields(ReviewRespDto.class, reviewEntity.id, reviewEntity.content, reviewEntity.score,
                                reviewEntity.userId, reviewEntity.content, reviewEntity.createdAt))
                .from(reviewEntity)
                .join(reviewEntity.productEntity, productEntity)
                .where(productEntity.id.eq(productId))
                .where(cursor == 0L ? null : reviewEntity.id.gt(cursor))
                .limit(size)
                .orderBy(reviewEntity.createdAt.desc())
                .fetch();
    }

    private void setReviewImage(List<ReviewRespDto> reviewDtos) {
        for (ReviewRespDto reviewDto : reviewDtos) {
            String imageUrl = queryFactory.select(imageFileEntity.originalFileName)
                    .from(imageFileEntity)
                    .where(imageFileEntity.reviewEntity.id.eq(reviewDto.getId()))
                    .fetchOne();
            reviewDto.addImageUrl(imageUrl);
        }
    }
}
