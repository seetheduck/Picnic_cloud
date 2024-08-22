package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pack.service.ReviewService;

@CrossOrigin("*")
@RestController
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	
	//@GetMapping("/places/{p_no}/reviews")
	//장소의 리뷰조회 get /places/{p_no}/reviews
	//public List<ReviewDto>
	
	//장소의 리뷰생성 post /places/{p_no}/reviews
		
	//장소의 리뷰수정 put /review/{r_no}
		
	//장소의 리뷰 삭제 delete /review/{r_no}
}
