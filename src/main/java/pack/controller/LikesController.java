package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pack.dto.LikeCountDto;
import pack.dto.LikesPlaceDto;
import pack.dto.LikesReviewDto;
import pack.service.LikesService;



@RestController
public class LikesController {
	
	@Autowired
	LikesService service;
	
	//좋아요 갯수 가져오기
	@GetMapping("/fleaMarket/likes/{no}")
	public LikeCountDto getLikes(@PathVariable("fleaBoardNo") int fleaBoardNo) {
		int count = service.countFleaLikes(fleaBoardNo);
		return new LikeCountDto(fleaBoardNo,count,false); //false 는 좋아요 여부 확인 없이 보낸다.
	}
	
	//좋아요 토글 
	@PatchMapping("/fleaMarket/likes")
	public LikeCountDto toggleLike(@RequestParam("userId") String userId, @RequestParam("fleaBoardNo") Integer fleaBoardNo) {
		int newLikeCount = service.toggleFleaMarketLike(userId, fleaBoardNo);
        boolean likedByUser = service.checkLikes(userId, fleaBoardNo);
        return new LikeCountDto(fleaBoardNo, newLikeCount, likedByUser);
    }

	// 리뷰 좋아요 로직
	// 5. 리뷰 좋아요 토글
	@PostMapping("/reviews/{ReviewNo}/likes-toggle")
	public void toggleLike(@PathVariable("ReviewNo") Integer ReviewNo, @RequestParam("userId") String userId) {
		LikesReviewDto dto = LikesReviewDto.builder()
				.reviewNo(ReviewNo).userId(userId)
				.build();
		service.toggleLike(dto);
	}

	// 6. 리뷰 좋아요 수 조회
	@GetMapping("/reviews/{ReviewNo}/likes-count")
	public int getLikesCount(@PathVariable("ReviewNo") Integer ReviewNo) {
		return service.getLikesCount(ReviewNo);
	}


	// 장소 좋아요 로직
	// 장소 좋아요 토글
	@PostMapping("/places/{PlaceNo}/likes-toggle")
	public void toggleLikePlace(@PathVariable("PlaceNo") Integer PlaceNo, @RequestParam("userId") String userId) {
		LikesPlaceDto dto = LikesPlaceDto.builder()
				.placeNo(PlaceNo).userId(userId)
				.build();
		service.toggleLike(dto);
	}

	// 장소 좋아요 수 조회
	@GetMapping("/places/{PlaceNo}/likes-count")
	public int getLikesPlaceCount(@PathVariable("PlaceNo") Integer PlaceNo) {
		return service.getLikesCount(PlaceNo);
	}


}
