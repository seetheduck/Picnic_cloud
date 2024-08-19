package pack.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pack.dto.PlaceDto;
import pack.repository.PlaceRepository;

@Service
public class PlaceService {
	@Autowired
	private PlaceRepository placeRepository;
	
	public List<PlaceDto> getPlaceList(String pCategory) {
		
		//entity to dto
		return placeRepository.findBypCategory(pCategory).stream()
				.map(PlaceDto::toDto)
				.collect(Collectors.toList());
	}
}
