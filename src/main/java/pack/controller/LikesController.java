package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import pack.dto.*;
import pack.service.LikesService;
import pack.service.PlaceService;
import pack.service.ReviewService;


@RestController
@CrossOrigin("*")
public class LikesController {
	
	@Autowired
	LikesService service;

	//플리마켓
	//좋아요 갯수 가져오기
	@GetMapping("/fleaMarket/favorite/{no}")
	public LikeCountDto getLikes(@PathVariable("fleaBoardNo") int fleaBoardNo) {
		int count = service.countFleaLikes(fleaBoardNo);
		return new LikeCountDto(fleaBoardNo,count,false); //false 는 좋아요 여부 확인 없이 보낸다.
	}
	
	//좋아요 토글 
	@PatchMapping("/fleaMarket/favorite")
	public LikeCountDto toggleLike(@RequestBody LikesDto request) {
		try {
			int newLikeCount = service.toggleFleaMarketLike(request.getUserId(), request.getFleaMarketNo());
			boolean likedByUser = service.checkLikes(request.getUserId(), request.getFleaMarketNo());
			return new LikeCountDto(request.getFleaMarketNo(), newLikeCount, likedByUser);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while processing your request");
		}
	}

	// 리뷰 좋아요 로직
	// 5. 리뷰 좋아요 토글
	@PostMapping("/reviews/{reviewNo}/likes-toggle")
	public ResponseEntity<String> toggleLike(@PathVariable("reviewNo") Integer reviewNo, @RequestParam("userId") String userId) {
		LikesReviewDto dto = LikesReviewDto.builder()
				.reviewNo(reviewNo)
				.userId(userId)
				.build();
		try {
			service.toggleReviewLike(dto);
			return ResponseEntity.ok("reviewLike toggled successfully."); // 성공적인 응답
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An reviewlikeerror occurred: " + e.getMessage()); // 오류 응답
		}
	}

	// 6. 리뷰 좋아요 수 조회
	@GetMapping("/reviews/{reviewNo}/likes-count")
	public ResponseEntity<Integer> getLikesCount(@PathVariable("reviewNo") Integer reviewNo) {
		try {
			int likeCount = service.getReviewLikesCount(reviewNo);
			return ResponseEntity.ok(likeCount); // 성공적인 응답
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 오류 응답
		}
	}

	//리뷰 좋아요 상태유지
		@GetMapping("/reviews/{reviewNo}/likes-status")
		public ResponseEntity<LikeStatusDto> getReviewLikeStatus(@PathVariable("reviewNo") int reviewNo, @RequestParam("userId") String userId) {
			try {
				boolean likedByUser = service.checkReviewLike(userId, reviewNo);
				int likeCount = service.getReviewLikesCount(reviewNo);
				return ResponseEntity.ok(new LikeStatusDto(likedByUser, likeCount));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		}

	//장소 좋아요 로직
	//특정 장소 좋아요 토글
	@PostMapping("/places/{placeNo}/likes-toggle")
	public ResponseEntity<LikesPlaceDto> togglePlaceLike(@RequestParam("userId") String userId, @PathVariable("placeNo") int placeNo) {
		LikesPlaceDto dto = LikesPlaceDto.builder()
				.userId(userId)
				.placeNo(placeNo)
				.build();
		try {
			// 좋아요 토글 처리
			service.togglePlaceLike(dto);

			// 좋아요 상태와 총 좋아요 수 가져오기
			boolean liked = service.checkPlaceLike(userId, placeNo);
			int likeCount = service.getPlaceLikesCount(placeNo);

			// LikesPlaceDto에 좋아요 상태와 좋아요 수를 설정하여 반환
			LikesPlaceDto responseDto = LikesPlaceDto.builder()
					.userId(userId)
					.placeNo(placeNo)
					.liked(liked)
					.likeCount(likeCount)
					.build();

			return ResponseEntity.ok(responseDto);  // LikesPlaceDto 반환
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);  // 오류 처리
		}
	}

	//특정 장소 좋아요 수 조회
	@GetMapping("/places/{placeNo}/likes-count")
	public ResponseEntity<Integer> getPlaceLikesCount(@PathVariable("placeNo") int placeNo) {
		try {
			int likeCount = service.getPlaceLikesCount(placeNo);
			return ResponseEntity.ok(likeCount); // 성공적인 응답
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 오류 응답
		}
	}
	//장소 좋아요 상태유지
	@GetMapping("/places/{placeNo}/likes-status")
	public ResponseEntity<LikeStatusDto> getPlaceLikeStatus(@PathVariable("placeNo") int placeNo, @RequestParam("userId") String userId) {
		try {
			boolean likedByUser = service.checkPlaceLike(userId, placeNo);
			int likeCount = service.getPlaceLikesCount(placeNo);
			return ResponseEntity.ok(new LikeStatusDto(likedByUser, likeCount));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}




}
