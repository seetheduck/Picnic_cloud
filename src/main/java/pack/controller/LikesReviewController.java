package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pack.dto.LikesReviewDto;
import pack.service.LikesReviewService;

@CrossOrigin("*")
@RestController
@RequestMapping("/reviews")
public class LikesReviewController {
	@Autowired
	private LikesReviewService likesReviewService;

	// 리뷰 좋아요 수 조회
	@GetMapping("/{placeNo}/likes-count")
	public int getLikesCount(@PathVariable("placeNo") Integer placeNo) {
		return likesReviewService.getLikesCount(placeNo);
	}

	// 리뷰 좋아요 토글
	@PostMapping("/{placeNo}/likes-toggle")
	public void toggleLike(@PathVariable("placeNo") Integer placeNo, @RequestParam("userId") String userId) {
		LikesReviewDto dto = LikesReviewDto.builder()
									.placeNo(placeNo).userId(userId)
									.build();
		likesReviewService.toggleLike(dto);
	}

	
}
