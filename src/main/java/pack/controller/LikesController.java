package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pack.dto.LikeCountDto;
import pack.dto.UserRequestDto;
import pack.service.LikesService;



@RestController
@CrossOrigin("*")
public class LikesController {
	
	@Autowired
	LikesService service;
	
	//플리마켓 좋아요 갯수 가져오기
	@GetMapping("/fleaMarket/favorite/{no}")
	public LikeCountDto getLikes(@PathVariable("no") int fleaBoardNo) {
		int count = service.countFleaLikes(fleaBoardNo);
		return new LikeCountDto(fleaBoardNo,count,false); //false 는 좋아요 여부 확인 없이 보낸다.
	}
	
	//플리마켓 좋아요 토글
	@PatchMapping("/fleaMarket/favorite/{no}")
	public LikeCountDto toggleLike(@RequestBody UserRequestDto dto, @PathVariable("no") Integer fleaBoardNo) {
//		System.out.println(dto +"  -----  "+ fleaBoardNo);
		String userId = dto.getUserid();
		int newLikeCount = service.toggleFleaMarketLike(userId, fleaBoardNo);
        boolean likedByUser = service.checkLikes(userId, fleaBoardNo);
        return new LikeCountDto(fleaBoardNo, newLikeCount, likedByUser);
    }
	
}
