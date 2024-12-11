package hanghae.review.shop.review.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanghae.review.IntegrationTestSupport;
import hanghae.review.global.util.ErrorMessage;
import hanghae.review.global.util.ReviewConst;
import hanghae.review.shop.product.domain.Product;
import hanghae.review.shop.product.service.port.ProductRepository;
import hanghae.review.shop.review.controller.req.ReviewCreateReqDto;
import java.util.List;
import java.util.Map;
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
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.save(createProduct(2.2f, 2L));
    }

    @Test
    @DisplayName("상품에 대한 리뷰를 생성할 수 있다")
    void registerReview() throws Exception {
        // given
        Long productId = 1L;
        ReviewCreateReqDto request = new ReviewCreateReqDto(1L, 2.2f, "내용");

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorMessage.NOT_FOUND_PRODUCT.getMessage()));
    }

    @Nested
    @DisplayName("요청 값이 유효성 검사에 통과하지 못하면 에러를 반환한다")
    class validateCheck{
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
        @DisplayName("내용이 2~15자 이내가 아니라면 에러를 반환한다")
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
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("내용은 2~15자로 입력해주세요"));
        }
    }


    private Product createProduct(float score, long reviewCount) {
        return Product.builder()
                .score(score)
                .reviewCount(reviewCount)
                .build();
    }

}