package pack.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import pack.dto.PlaceDto;
import pack.entity.PlaceEntity;
import pack.entity.ReviewEntity;
import pack.repository.PlaceRepository;
import pack.repository.PlaceSpecification;

@Service
public class PlaceService {
	@Autowired
	private PlaceRepository placeRepository;

//	@Autowired
//	private ReviewRepository reviewRepository;

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

//	// 리뷰 추가 후 장소의 정보 업데이트(평점, 리뷰수)
//	public void updatePlaceAfterReview(int placeNo) {
//		PlaceEntity place = placeRepository.findById(placeNo)
//				.orElseThrow(() -> new RuntimeException("Place not found"));
//		//orElseThrow를 사용하여 Optional이 비어 있는 경우에 예외를 발생시키고, 비어 있지 않은 경우에는 값을 안전하게 추출
//		//이 방법은 코드에서 Optional의 값을 안전하게 처리하는 표준적인 방식. 예외처리하지 않으면 에러떨어짐
//
//		//리뷰수 카운트
//		int reviewCount = reviewRepository.countReviewsByPlaceNo(placeNo);
//
//		//리뷰의 평균 평점 계산
//		float averagePoint = reviewRepository.findByPlaceNoOrderByCreateDateDesc(placeNo)
//				.stream()
//				.map(ReviewEntity::getPoint)
//				.reduce(0f, Float::sum) / Math.max(reviewCount, 1); // 리뷰 수가 0일 때 분모가 0이 되지 않도록
//
//		place.setReviewCount(reviewCount);
//		place.setPoint(averagePoint);
//
//		placeRepository.save(place);
//	}


}