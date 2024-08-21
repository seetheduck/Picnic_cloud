package pack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pack.dto.PlaceDto;
import pack.entity.PlaceEntity;
import pack.service.PlaceService;

@CrossOrigin("*")
@RestController
@RequestMapping("/places")
public class PlaceController {
	@Autowired
	private PlaceService placeService;
 
	//카테고리별 쿼리메소드와 통합. 같은 방법으로 매핑(get)이기에 통합이 훨씬 restful하다.
	//키워드없이도 카테고리만으로검색되도록. required = false, defaultValue = ""
	@GetMapping
	public List<PlaceDto> getPlacesByCategoryandKeyword(
			@RequestParam("pCategory") String pCategory, 
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword){
		return placeService.findPlacesBypCategoryandKeyword(pCategory, keyword);
	}
}
