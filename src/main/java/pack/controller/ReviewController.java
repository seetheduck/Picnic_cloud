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
import pack.service.JwtService;
import pack.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private JwtService jwtService;

    // 1. 선택한 장소의 리뷰들 조회. 최신순 나열, 페이징처리.
    @GetMapping("/{placeNo}")
    public ResponseEntity<Page<ReviewDto>> getReviewsByPlaceNo(
            @PathVariable("placeNo") int placeNo,
            @RequestParam(name = "page" ,defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate"));
        Page<ReviewDto> reviews = reviewService.findReviewsByPlaceNo(placeNo, pageable);
        return ResponseEntity.ok(reviews);
    }

    //2. 장소의 리뷰생성
    @PostMapping("/{placeNo}")
    public ResponseEntity<ReviewDto> createReview(
            @PathVariable("placeNo") int placeNo,
            @RequestBody ReviewDto reviewDto) {
        reviewDto.setPlaceNo(placeNo);
        ReviewDto createdReview = reviewService.saveReview(reviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    //3. 리뷰 수정
    @PutMapping("/{no}")
    public ResponseEntity<ReviewDto> updateReview(
            @PathVariable("no") int no,
            @RequestBody ReviewDto reviewDto,
            @RequestHeader("Authorization") String tokenHeader) { // 토큰을 헤더로 받음
        try {
            String token = tokenHeader.replace("Bearer ", ""); // "Bearer " 부분을 제거하고 토큰만 추출
            String userId = jwtService.getUserFromToken(token); // 토큰에서 사용자 ID 추출

            // 사용자 검증 및 리뷰 업데이트 처리
            ReviewDto updatedReview = reviewService.updateReview(no, reviewDto, userId);
            return ResponseEntity.ok(updatedReview);
        } catch (ForbiddenException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    //4. 리뷰 삭제
    @DeleteMapping("/{no}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable("no") int no,
            @RequestHeader("Authorization") String token) {
        try {
            // Bearer 문자열을 제거한 실제 토큰 추출
            String jwtToken = token.replace("Bearer ", "");

            boolean success = reviewService.deleteReview(no, jwtToken);
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
