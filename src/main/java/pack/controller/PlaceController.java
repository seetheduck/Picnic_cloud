package pack.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pack.dto.PlaceDto;
import pack.service.PlaceService;

@CrossOrigin("*")
@RestController
@RequestMapping("/places")
public class PlaceController {
	
	@Autowired
	private PlaceService placeService;

	//검색 조건을 처리하는 로직은 서비스 레이어에 위치하는 것이 좋습니다. 
	//컨트롤러에서는 간단하게 요청을 받아서 서비스 메서드를 호출하는 것이 바람직합니다
	
	//카테고리별 및 검색어를 통한 장소 목록 조회. 페이징처리 추가.
	@GetMapping
    public Page<PlaceDto> getPlacesByPlaceTypeAndKeyword(
			@RequestParam(value = "placeType") String placeType,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "userId") String userId,
			Pageable pageable) {
		return placeService.findPlacesByPlaceTypeAndKeyword(placeType, keyword, pageable,userId);
	}

	//특정 장소 상세정보. //places/3
	@GetMapping("/{no}")
	public ResponseEntity<PlaceDto> getPlacesByNo(@PathVariable("no") int no, @RequestParam("userId") String userId) {
		return placeService.findPlacesByNo(no, userId)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
}
