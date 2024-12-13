package hanghae.review.shop.review.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanghae.review.IntegrationTestSupport;
import hanghae.review.global.util.ErrorMessage;
import hanghae.review.global.util.ReviewConst;
import hanghae.review.shop.product.infrastructure.ProductEntity;
import hanghae.review.shop.product.infrastructure.ProductJpaRepository;
import hanghae.review.shop.review.controller.req.ReviewCreateReqDto;
import hanghae.review.shop.review.infrastructure.ReviewEntity;
import hanghae.review.shop.review.infrastructure.ReviewJpaRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


class ReviewControllerTest extends IntegrationTestSupport {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductJpaRepository productRepository;

    @Autowired
    private ReviewJpaRepository reviewRepository;

    @BeforeEach
    void setUp() {
        ProductEntity productEntity = productRepository.save(createProductEntity(2.2f, 2L));
        reviewRepository.save(createReviewEntity(1L, 2.2f, productEntity, LocalDateTime.of(2024,12,12,12,32,00)));
        reviewRepository.save(createReviewEntity(2L, 3.2f, productEntity, LocalDateTime.of(2024,12,12,13,00,00)));
    }

    @Test
    @DisplayName("상품에 대한 리뷰를 생성할 수 있다")
    void registerReview() throws Exception {
        // given
        Long productId = 1L;
        ReviewCreateReqDto request = new ReviewCreateReqDto(3L, 2.2f, "내용");

        // when
        ResultActions resultActions = mockMvc.perform(post("/products/{productId}/reviews", productId)
                        .content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("상품이 없으면 에러를 반환한다")
    void NoProductWithError() throws Exception {
        // given
        ReviewCreateReqDto request = new ReviewCreateReqDto(1L, 2.2f, "내용");

        // when
        ResultActions resultActions = mockMvc.perform(post("/products/{productId}/reviews", 3L)
                        .content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(-1))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.message").value(ErrorMessage.NOT_FOUND_PRODUCT.getMessage()));
    }

    @Nested
    @DisplayName("요청 값이 유효성 검사에 통과하지 못하면 에러를 반환한다")
    class validateCheck {
        @Test
        @DisplayName("유저아이디가 빈 값이면 에러를 반환한다")
        void validateUserId() throws Exception {
            // given
            ReviewCreateReqDto request = new ReviewCreateReqDto(null, 2.2f, "내용");

            // when
            ResultActions resultActions = mockMvc.perform(post("/products/{productId}/reviews", 3L)
                            .content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print());

            // then
            resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(-1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ReviewConst.VALIDATE_FAIL))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value("유저 아이디를 입력해주세요"));
        }

        @Test
        @DisplayName("스코어가 0~5 이내가 아니면 에러를 반환한다")
        void validateScoreRange() throws Exception {
            // given
            ReviewCreateReqDto request = new ReviewCreateReqDto(1L, 5.1f, "내용");

            // when
            ResultActions resultActions = mockMvc.perform(post("/products/{productId}/reviews", 3L)
                            .content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print());

            // then
            resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(-1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ReviewConst.VALIDATE_FAIL))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.score").value("점수는 0~5사이로만 입력하실 수 있습니다"));
        }

        @Test
        @DisplayName("내용이 2~50자 이내가 아니라면 에러를 반환한다")
        void NotRangeContent() throws Exception {
            // given
            ReviewCreateReqDto request = new ReviewCreateReqDto(1L, 5.1f, "내");

            // when
            ResultActions resultActions = mockMvc.perform(post("/products/{productId}/reviews", 3L)
                            .content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print());

            // then
            resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(-1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ReviewConst.VALIDATE_FAIL))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("내용은 2~50자로 입력해주세요"));
        }
    }

    @Test
    @DisplayName("상품의 리뷰들을 오름차순으로 조회할 수 있다")
    void getProductReviews() throws Exception {
        // given
        Long productId = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(get("/products/{productId}/reviews", productId))
                .andDo(MockMvcResultHandlers.print());

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalCount").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.score").value(Math.round(2.2f/2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reviews[0].userId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reviews[1].userId").value(1));
    }


    private ProductEntity createProductEntity(float score, long reviewCount) {
        return ProductEntity.builder()
                .score(score)
                .reviewCount(reviewCount)
                .build();
    }

    private ReviewEntity createReviewEntity(long userId, float score, ProductEntity productEntity, LocalDateTime currentTime){
        return ReviewEntity.builder()
                .userId(userId)
                .content("내용")
                .score(score)
                .createdAt(currentTime)
                .updatedAt(currentTime)
                .productEntity(productEntity)
                .build();
    }

}