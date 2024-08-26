package pack.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import pack.dto.PlaceDto;
import pack.entity.PlaceEntity;
import pack.repository.PlaceRepository;
import pack.repository.PlaceSpecification;

@Service
public class PlaceService {
	@Autowired
	private PlaceRepository placeRepository;
	
	//테마별 장소 목록. 검색(시설명, 주소)어가 있을경우 조건추가.
	public List<PlaceDto> findPlacesByTypeAndKeyword(String placeType, String keyword) {
        Specification<PlaceEntity> spec = PlaceSpecification.hasKeyword(placeType, keyword)
        														.and(PlaceSpecification.orderByPointAndLikeCnt());
        return placeRepository.findAll(spec).stream()
        		.map(PlaceEntity::toPlaceDto)
        		.collect(Collectors.toList());
    }
	//선택한 장소 1곳 상세정보.
	public Optional<PlaceDto> findPlacesByNo(int no){
		
		//entity to dto
		return placeRepository.findByNo(no)
				.map(PlaceEntity::toPlaceDto);
	}

}
