package pack.service;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.LikesPlaceDto;
import pack.dto.PlaceDto;
import pack.entity.PlaceEntity;
import pack.repository.LikesRepository;
import pack.repository.PlaceRepository;
import pack.repository.PlaceSpecification;
import pack.repository.ReviewRepository;

import java.util.Optional;

@Service
public class PlaceService {
	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private LikesService likesService;

	@Resource
	private AsyncPlaceService asyncPlaceService; // 비동기 메서드를 호출할 프록시 객체

	// 검색이 없는 경우: 단순히 유형별 장소를 페이징 처리
	public Page<PlaceDto> findPlacesByType(String placeType, Pageable pageable) {
		Specification<PlaceEntity> spec = PlaceSpecification.hasKeyword(placeType, null)
				.and(PlaceSpecification.orderByPointAndLikeCnt());
		Page<PlaceEntity> placePage = placeRepository.findAll(spec, pageable);
		return placePage.map(PlaceEntity::toPlaceDto);
	}


	// 검색이 있는 경우: 유형별 장소에 추가적으로 키워드로 검색하고 정렬
	public Page<PlaceDto> findPlacesByPlaceTypeAndKeyword(String placeType, String keyword, Pageable pageable) {
		Specification<PlaceEntity> spec = PlaceSpecification.hasKeyword(placeType, keyword)
				.and(PlaceSpecification.orderByPointAndLikeCnt());
		Page<PlaceEntity> placePage = placeRepository.findAll(spec, pageable);

		return placePage.map(PlaceEntity::toPlaceDto);
	}

	// 선택한 장소 1곳 상세정보.
	public Optional<PlaceDto> findPlacesByNo(int no) {
		// entity to dto
		return placeRepository.findByNo(no)
				.map(PlaceEntity::toPlaceDto);
	}

	// 리뷰 추가 후 장소 정보 업데이트
	@Transactional
	public void updatePlaceAfterReview(int placeNo) {
		// 장소 엔티티 조회
		PlaceEntity placeEntity = placeRepository.findById(placeNo)
				.orElseThrow(() -> new EntityNotFoundException("Place not found"));

		// 리뷰 수 카운트
		int reviewCount = reviewRepository.countReviewsByPlaceNo(placeNo);
		placeEntity.setReviewCount(reviewCount);

		// 평점 계산
		Float averagePoint = placeRepository.findAveragePointByPlaceNo(placeNo);
		if (averagePoint == null) {
			averagePoint = 0.0f; // 리뷰가 없는 경우 기본값 설정
		}
		placeEntity.setPoint(averagePoint);

		// 장소 엔티티 저장
		placeRepository.save(placeEntity);
	}

	// 장소의 좋아요 토글 기능
	@Transactional
	public void togglePlaceLike(String userId, int placeNo) {
		// 좋아요 토글
		likesService.togglePlaceLike(LikesPlaceDto.builder()
				.userId(userId)
				.placeNo(placeNo)
				.build());

		// 장소의 좋아요 수 업데이트
		// 장소의 좋아요 수 업데이트를 비동기로 호출
		asyncPlaceService.updatePlaceLikeCount(placeNo);
	}

	// 장소의 좋아요 수 카운트
	public int getPlaceLikesCount(int placeNo) {
		return likesService.getPlaceLikesCount(placeNo);

	}
}
