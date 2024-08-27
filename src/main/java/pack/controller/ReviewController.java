package pack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pack.dto.ReviewDto;
import pack.service.ReviewService;

@CrossOrigin("*")
@RestController
@RequestMapping("/reviews")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	
	//장소의 리뷰조회 /reviews/{placeNo}
	@GetMapping("/{placeNo}") 
	public List<ReviewDto> getReviewsByPlaceNo(@PathVariable("placeNo") int placeNo){
		return reviewService.findReviewsByPlaceNo(placeNo);
	}

	//장소의 리뷰생성 /reviews/{placeNo}
	@PostMapping("/{placeNo}")
	public ReviewDto createReview(@PathVariable("placeNo") int placeNo, @RequestBody ReviewDto reviewDto) {
		reviewDto.setPlaceNo(placeNo);
		return reviewService.saveReview(reviewDto);
	}
	
	//리뷰 수정/reviews/{no}
	@PutMapping("/{no}")
	public ReviewDto updateReview(@PathVariable("no") int no, @RequestBody ReviewDto reviewDto) {
		reviewDto.setNo(no); 
		return reviewService.saveReview(reviewDto);
	}
	
	//리뷰 삭제 /reviews/{no}
	@DeleteMapping("/{no}")
	public void deleteReview(@PathVariable("no") int no) {
		reviewService.deleteReview(no);
	}
	
}
