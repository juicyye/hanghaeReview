package hanghae.review.shop.review.controller.resp;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRespDto{

    private Long id;
    private Long userId;
    private Float score;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;

    public void addImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

}
