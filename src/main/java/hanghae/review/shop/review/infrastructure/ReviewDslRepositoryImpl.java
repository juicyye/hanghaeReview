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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReviewDslRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public ProductReviewRespDto findAllProductReviews(Long productId, Pageable pageable) {
        List<ReviewRespDto> reviewRespDtos = getProductReviews(productId, pageable);
        setReviewImage(reviewRespDtos);

        ProductReviewRespDto reviewDto = getReviews(productId);
        reviewDto.setReviewInfo(pageable.getPageNumber(), reviewRespDtos);
        return reviewDto;
    }

    private List<ReviewRespDto> getProductReviews(Long productId, Pageable pageable) {
        return queryFactory.select(
                        Projections.fields(ReviewRespDto.class, reviewEntity.id, reviewEntity.content, reviewEntity.score,
                                reviewEntity.content, reviewEntity.createdAt))
                .from(reviewEntity)
                .join(reviewEntity.productEntity, productEntity)
                .where(productEntity.id.eq(productId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reviewEntity.createdAt.asc())
                .fetch();
    }

    private void setReviewImage(List<ReviewRespDto> reviewDtos) {
        for (ReviewRespDto reviewDto : reviewDtos) {
            String imageUrl = queryFactory.select(imageFileEntity.originalFileName)
                    .from(imageFileEntity)
                    .where(imageFileEntity.reviewEntity.id.eq(reviewDto.id()))
                    .fetchOne();
            ReviewRespDto.addImage(reviewDto, imageUrl);
        }
    }

    private ProductReviewRespDto getReviews(Long productId) {
        return queryFactory.select(
                        Projections.fields(ProductReviewRespDto.class, reviewEntity.count().as("totalCount"),
                                reviewEntity.score))
                .from(reviewEntity)
                .where(productEntity.id.eq(productId))
                .fetchOne();
    }
}
