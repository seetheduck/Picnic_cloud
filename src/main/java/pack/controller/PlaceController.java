package pack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import pack.dto.PlaceDto;

import pack.service.PlaceService;

@CrossOrigin("*")
@RestController("/places")
public class PlaceController {
	@Autowired
	private PlaceService placeService;
	
	@GetMapping("/{pCategory}")
	public List<PlaceDto> getList(@PathVariable("pCategory") String pCategory) {
		return placeService.getPlaceList(pCategory);
	}

}
