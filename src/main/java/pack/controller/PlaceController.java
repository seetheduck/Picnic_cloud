package pack.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
 
	//카테고리별 쿼리메소드와 통합. 같은 방법으로 매핑(get)이기에 통합이 훨씬 restful하다.
	//카테고리별 및 검색어를 통한 장소 목록 조회 //places?placeType=waterpark&keyword=aqua
    @GetMapping
    public List<PlaceDto> getPlacesByTypeAndKeyword(
            @RequestParam(value = "placeType") String placeType, 
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        return placeService.findPlacesByTypeAndKeyword(placeType, keyword);
    }

	//특정 장소 상세정보. //places/3
	@GetMapping("/{no}")
	public Optional<PlaceDto> getPlacesByNo(@PathVariable("no") int no){
		
		return placeService.findPlacesByNo(no);
	}
}
