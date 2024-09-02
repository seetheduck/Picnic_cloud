package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pack.dto.ReviewDto;
import pack.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    //1. 장소의 리뷰조회. 페이징포함. /reviews/{placeNo}
    @GetMapping("/{placeNo}")
    public Page<ReviewDto> getReviewsByPlaceNo(@PathVariable("placeNo") int placeNo,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate"));
        return reviewService.findReviewsByPlaceNo(placeNo, pageable);
    }

    //2. 장소의 리뷰생성 /reviews/{placeNo}
    @PostMapping("/{placeNo}")
    public ReviewDto createReview(@PathVariable("placeNo") int placeNo, @RequestBody ReviewDto reviewDto) {
        reviewDto.setPlaceNo(placeNo);
        return reviewService.saveReview(reviewDto);
    }

    //3. 리뷰 수정/reviews/{no}
    @PutMapping("/{no}")
    public ReviewDto updateReview(@PathVariable("no") int no,
                                  @RequestBody ReviewDto reviewDto,
                                  @RequestParam("userId") String userId) {
        ReviewDto updatedReview = reviewService.updateReview(no, reviewDto, userId);
        if (updatedReview == null) {
            // 권한이 없는 경우 403 Forbidden 반환
            return null; // 이 경우 프론트에서 처리해야 할 필요가 있음
        }
        return updatedReview;
    }

    //4. 리뷰 삭제 /reviews/{no}
    @DeleteMapping("/{no}")
    public void deleteReview(@PathVariable("no") int no,
                             @RequestParam("userId") String userId) {
        boolean success = reviewService.deleteReview(no, userId);
        if (!success) {
            // 권한이 없는 경우 403 Forbidden 반환
            // 일반적으로 이 경우 HTTP 403 상태 코드를 반환해야 하지만, 컨트롤러에서는 빈 응답을 반환함
        }
    }
}
