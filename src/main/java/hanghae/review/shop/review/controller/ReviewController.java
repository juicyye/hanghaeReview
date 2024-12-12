package hanghae.review.shop.review.controller;

import hanghae.review.shop.review.controller.req.ReviewCreateReqDto;
import hanghae.review.shop.review.controller.resp.ProductReviewRespDto;
import hanghae.review.shop.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{productId}/reviews")
    public ResponseEntity<?> registerReview(@PathVariable("productId") Long productId,
                                            @Valid @RequestBody ReviewCreateReqDto createReqDto,
                                            @RequestPart(value = "file", required = false) MultipartFile file) {
        reviewService.create(productId, createReqDto, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{productId}/reviews")
    public ResponseEntity<?> getProductReviews(@PathVariable("productId") Long productId, @RequestParam(defaultValue = "0") Long cursor,
                                               @RequestParam(defaultValue = "10") int size) {
        ProductReviewRespDto response = reviewService.fetchProductReviews(productId, cursor, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

