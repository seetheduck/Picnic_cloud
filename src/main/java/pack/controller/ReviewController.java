package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.dto.ReviewDto;
import pack.exception.ForbiddenException;
import pack.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    // 1. 선택한 장소의 리뷰들 조회. 최신순 나열, 페이징처리. /reviews/{placeNo}
    @GetMapping("/{placeNo}")
    public ResponseEntity<Page<ReviewDto>> getReviewsByPlaceNo(
            @PathVariable("placeNo") int placeNo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate"));
        Page<ReviewDto> reviews = reviewService.findReviewsByPlaceNo(placeNo, pageable);
        return ResponseEntity.ok(reviews);
    }


    //2. 장소의 리뷰생성 /reviews/{placeNo}
    @PostMapping("/{placeNo}")
    public ResponseEntity<ReviewDto> createReview(
            @PathVariable("placeNo") int placeNo,
            @RequestBody ReviewDto reviewDto) {
        reviewDto.setPlaceNo(placeNo);
        ReviewDto createdReview = reviewService.saveReview(reviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    //3. 리뷰 수정/reviews/{no}
    @PutMapping("/{no}")
    public ResponseEntity<ReviewDto> updateReview(
            @PathVariable("no") int no,
            @RequestBody ReviewDto reviewDto) {
        try {
            // ReviewDto 안에 있는 id 값을 사용
            String id = reviewDto.getId();

            ReviewDto updatedReview = reviewService.updateReview(no, reviewDto, id);
            return ResponseEntity.ok(updatedReview);
        } catch (ForbiddenException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }



    //4. 리뷰 삭제 /reviews/{no}
    @DeleteMapping("/{no}")
    public ResponseEntity<Void> deleteReview(
                @PathVariable("no") int no,
                @RequestParam("id") String id) {
            try {
                boolean success = reviewService.deleteReview(no, id);
                if (success) {
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            } catch (ForbiddenException ex) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

    }
