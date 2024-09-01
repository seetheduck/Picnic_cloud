package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pack.dto.LikeCountDto;
import pack.service.LikesService;

@RestController
@RequestMapping("/fleaMarket")
public class LikesController {

	@Autowired
	private LikesService service;

	// 좋아요 갯수 가져오기
	@GetMapping("/likes/{fleaBoardNo}")
	public LikeCountDto getLikes(@PathVariable int fleaBoardNo) {
		int count = service.countFleaLikes(fleaBoardNo);
		return new LikeCountDto(fleaBoardNo, count, false); // false는 좋아요 여부 확인 없이 반환
	}

	// 좋아요 토글
	@PatchMapping("/likes")
	public LikeCountDto toggleLike(@RequestParam String userId, @RequestParam int fleaBoardNo) {
		int newLikeCount = service.toggleFleaMarketLike(userId, fleaBoardNo);
		boolean likedByUser = service.checkLikes(userId, fleaBoardNo);
		return new LikeCountDto(fleaBoardNo, newLikeCount, likedByUser);
	}

}
