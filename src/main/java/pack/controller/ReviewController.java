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
	
	//장소의 리뷰조회 
	@GetMapping("/{rPno}") 
	public List<ReviewDto> getReviewsByrPno(@PathVariable("rPno") int rPno){
		return reviewService.findReviewsByrPno(rPno);
	}

	//장소의 리뷰생성
	@PostMapping("/{rPno}")
	public ReviewDto createReview(@PathVariable("rPno") int rPno, @RequestBody ReviewDto reviewDto) {
		reviewDto.setRPno(rPno); 
		return reviewService.saveReview(reviewDto);
	}

	//장소의 리뷰수정 
	@PutMapping("/{rNo}")
	public ReviewDto updateReview(@PathVariable("rNo") int rNo, @RequestBody ReviewDto reviewDto) {
		reviewDto.setRNo(rNo);
		return reviewService.saveReview(reviewDto);
	}
		
	//장소의 리뷰 삭제 
	
}
