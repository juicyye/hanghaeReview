package hanghae.review.shop.review.controller;

import hanghae.review.shop.review.controller.req.ReviewCreateReqDto;
import hanghae.review.shop.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{productId}/reviews")
    public ResponseEntity<?> registerReview(@PathVariable("productId") Long productId, @Valid @RequestBody ReviewCreateReqDto createReqDto,
                                            MultipartFile file) {
        reviewService.create(productId, createReqDto,file);
        return ResponseEntity.ok().build();
    }

}

