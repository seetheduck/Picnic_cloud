package pack.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import pack.dto.PlaceDto;
import pack.entity.PlaceEntity;
import pack.repository.PlaceRepository;

@Service
public class PlaceService {
	@Autowired
	private PlaceRepository placeRepository;
	
	public List<PlaceDto> findPlacesBypCategoryandKeyword(String pCategory, String keyword) {
		
		//entity to dto
		return placeRepository.findBypCategoryandKeyword(pCategory, keyword).stream()
				.map(PlaceEntity::toPlaceDto)
				.collect(Collectors.toList());
		
	}
	//선택한 장소 1곳 상세정보.
	public Optional<PlaceDto> findPlacesBypNo(int pNo){
		
		//entity to dto
		return placeRepository.findBypNo(pNo)
				.map(PlaceEntity::toPlaceDto);
				
	}

}
