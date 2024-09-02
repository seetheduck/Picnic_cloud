package pack.service;

import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import pack.dto.PlaceDto;
import pack.entity.PlaceEntity;
import pack.entity.ReviewEntity;
import pack.repository.PlaceRepository;
import pack.repository.PlaceSpecification;
import pack.repository.ReviewRepository;

@Service
public class PlaceService {
	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private ReviewRepository reviewRepository;

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


// 리뷰 추가 후 장소 정보 업데이트
public void updatePlaceAfterReview(int placeNo) {
	// 장소 엔티티 조회
	PlaceEntity placeEntity = placeRepository.findById(placeNo)
			.orElseThrow(() -> new EntityNotFoundException("Place not found"));

	// 리뷰 수 카운트
	int reviewCount = reviewRepository.countReviewsByPlaceNo(placeNo);
	placeEntity.setReviewCount(reviewCount);

	// 평점 계산
	Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, "createDate"));
	float averagePoint = (float) reviewRepository.findByPlaceNoOrderByCreateDateDesc(placeNo, pageable).stream()
			.mapToDouble(ReviewEntity::getPoint)
			.average()
			.orElse(0.0);
	placeEntity.setPoint(averagePoint);

	// 장소 엔티티 저장
	placeRepository.save(placeEntity);
}

	/*
public void updatePlaceAfterReview(int placeNo) {
	// 장소 엔티티 조회
	PlaceEntity placeEntity = placeRepository.findById(placeNo)
			.orElseThrow(() -> new EntityNotFoundException("Place not found"));

	// 리뷰 수 카운트
	int reviewCount = reviewRepository.countReviewsByPlaceNo(placeNo);
	placeEntity.setReviewCount(reviewCount);

	// 평점 계산
	Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, "createDate"));
	float averagePoint = (float) reviewRepository.findByPlaceNoOrderByCreateDateDesc(placeNo, pageable).stream()
			.mapToDouble(ReviewEntity::getPoint)
			.average()
			.orElse(0.0);
	placeEntity.setPoint(averagePoint);

	// 장소 엔티티 저장
	placeRepository.save(placeEntity);*/
}




