package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pack.service.LikesPlaceService;

@Controller
@RequestMapping("/places")
public class LikesPlaceController {
	@Autowired
	private LikesPlaceService likesPlaceService;
	
	//장소번호에 따른 좋아요 수 조회
	 @GetMapping("/{placeNo}/likes-count")
	 public int getLikesCount(@PathVariable Integer placeNo) {
	        return likesPlaceService.getLikesCountByPlaceNo(placeNo);
	 }
	 
	//좋아요 토글
	@PostMapping("/{placeNo}/likes-toggle")
	public long toggleLike(@PathVariable Integer placeNo, @RequestParam String userId) {
        return likesPlaceService.toggleLike(placeNo, userId);
    }
}
