package pack.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	//페이징기능추가
	//검색기능있을때와 없을때 나누는이유 : 성능최적화, 사용자 경험 향상
	//list객체가 아닌 page객체를 사용한 이유: 데이터로딩을 막기위해.
	//Page 객체는 페이징 처리를 지원하여 데이터 양이 많을 때 발생할 수 있는 성능 문제를 줄이는 데 도움을 줍니다
	
	/*
	//테마별 장소 목록. 검색(시설명, 주소)어가 있을경우 조건추가.
	public List<PlaceDto> findPlacesByTypeAndKeyword(String placeType, String keyword) {
        Specification<PlaceEntity> spec = PlaceSpecification.hasKeyword(placeType, keyword)
        														.and(PlaceSpecification.orderByPointAndLikeCnt());
        return placeRepository.findAll(spec).stream()
        		.map(PlaceEntity::toPlaceDto)
        		.collect(Collectors.toList());
    }*/
	// 검색이 없는 경우: 단순히 유형별 장소를 페이징 처리
    public Page<PlaceDto> findPlacesByType(String placeType, Pageable pageable) {
        Page<PlaceEntity> placePage = placeRepository.findByPlaceType(placeType, pageable);
        return placePage.map(PlaceEntity::toPlaceDto);
    }

    // 검색이 있는 경우: 유형별 장소에 추가적으로 키워드로 검색하고 정렬
    public Page<PlaceDto> findPlacesByPlaceTypeAndKeyword(String placeType, String keyword, Pageable pageable) {
        Specification<PlaceEntity> spec = PlaceSpecification.hasKeyword(placeType, keyword)
        												.and(PlaceSpecification.orderByPointAndLikeCnt());
        Page<PlaceEntity> placePage = placeRepository.findAll(spec, pageable);
        
        return placePage.map(PlaceEntity::toPlaceDto);
    }
	
	//선택한 장소 1곳 상세정보.
	public Optional<PlaceDto> findPlacesByNo(int no){
		
		//entity to dto
		return placeRepository.findByNo(no)
				.map(PlaceEntity::toPlaceDto);
	}
	
}
