package hanghae.review.shop.review.controller.resp;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@NoArgsConstructor
@Getter
public class ProductReviewRespDto {

    private Long totalCount;
    private Float score;
    private int cursor;
    private List<ReviewRespDto> reviewRespDtos = new ArrayList<>();

    public void setReviewInfo(int cursor, List<ReviewRespDto> reviewRespDtos) {
        this.cursor = cursor;
        this.reviewRespDtos = reviewRespDtos;
    }
}
