package hanghae.review.shop.review.controller;

import hanghae.review.shop.imagefile.service.ImageFileService;
import hanghae.review.shop.product.domain.Product;
import hanghae.review.shop.product.service.ProductService;
import hanghae.review.shop.review.controller.req.ReviewCreateReqDto;
import hanghae.review.shop.review.controller.resp.ProductReviewRespDto;
import hanghae.review.shop.review.controller.resp.ReviewRespDto;
import hanghae.review.shop.review.domain.Review;
import hanghae.review.shop.review.service.ReviewService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private final ProductService productService;

    @PostMapping("/{productId}/reviews")
    public ResponseEntity<?> registerReview(@PathVariable("productId") Long productId,
                                            @Valid @RequestPart("createReqDto") ReviewCreateReqDto createReqDto,
                                            @RequestPart(value = "file", required = false) MultipartFile file) {
        reviewService.create(productId, createReqDto, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{productId}/reviews")
    public ResponseEntity<?> getProductReviews(@PathVariable("productId") Long productId,
                                               @RequestParam(defaultValue = "0") Long cursor,
                                               @RequestParam(defaultValue = "10") int size) {
        Product product = productService.fetchProduct(productId);
        List<Review> reviews = reviewService.fetchProductReviews(productId, cursor, size);
        List<ReviewRespDto> reviewRespDtos = reviews.stream().map(ReviewRespDto::new).toList();

        return new ResponseEntity<>(ProductReviewRespDto.of(product,reviewRespDtos, cursor), HttpStatus.OK);
    }
}

