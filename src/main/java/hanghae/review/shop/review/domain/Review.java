package hanghae.review.shop.review.domain;

import java.time.LocalDateTime;

public class Review {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
