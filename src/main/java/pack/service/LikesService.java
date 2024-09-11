package pack.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pack.dto.LikesPlaceDto;
import pack.dto.LikesReviewDto;
import pack.entity.LikesEntity;
import pack.entity.UserEntity;
import pack.repository.LikesRepository;
import pack.repository.PlaceRepository;
import pack.repository.ReviewRepository;
import pack.repository.UserRepository;

import java.util.Optional;

@Service
public class LikesService {

	@Autowired
	private LikesRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private PlaceRepository placeRepository;


	// 좋아요 토글
	@Transactional
	public int toggleFleaMarketLike(String userId, int fleaBoardNo) {
		Optional<UserEntity> userEntity = userRepository.findById(userId);
		if (userEntity == null) {
			throw new IllegalArgumentException("User not found");
		}

		LikesEntity existingLike = repository.findByUserIdAndFleaMarketNo(userId, fleaBoardNo);

		if (existingLike != null) {
			repository.delete(existingLike);
		} else {
			LikesEntity newLike = LikesEntity.builder()
					.no(repository.maxFavNum() + 1)
					.userId(userId)
					.fleaMarketNo(fleaBoardNo)
					.build();
			repository.save(newLike);
		}

		return repository.countByFleaMarketNo(fleaBoardNo);
	}

	// 좋아요 여부 확인
	public boolean checkLikes(String userId, Integer fleaMarketNo) {
		// UserEntity를 직접 반환하고, null이 아니라고 가정
		Optional<UserEntity> userEntity = userRepository.findById(userId);
		if (userEntity == null) {
			throw new IllegalArgumentException("User not found");
		}

		LikesEntity existingLike = repository.findByUserIdAndFleaMarketNo(userId, fleaMarketNo);
		return existingLike != null;
	}

	// 특정 게시물 좋아요 수 계산
	public int countFleaLikes(Integer fleaMarketNo) {
		return repository.countByFleaMarketNo(fleaMarketNo);
	}


	// 리뷰 좋아요 로직
	// 4. 좋아요 토글처리
	@Transactional
	public void toggleReviewLike(LikesReviewDto dto) {
		Optional<LikesEntity> currentLike = repository.findByUserIdAndReviewNo(dto.getUserId(),
				dto.getReviewNo());

		if (currentLike.isPresent()) {
			// 좋아요가 존재하면 삭제
			repository.deleteByUserIdAndReviewNo(dto.getUserId(), dto.getReviewNo());
			//리뷰의 좋아요 수 감소
			updateReviewLikeCnt(dto.getReviewNo(), -1);
		} else {
			// 좋아요가 존재하지 않으면 추가
			LikesEntity newLike = LikesReviewDto.toEntity(dto);
			repository.save(newLike);
			// 리뷰의 좋아요 수 증가
			updateReviewLikeCnt(dto.getReviewNo(), 1);

		}

	}
	//4-1. 좋아요가 추가되거나 삭제될 때 호출되어 리뷰의 좋아요 수를 항상 최신 상태로 유지하도록
	private void updateReviewLikeCnt(int reviewNo, int increment) {
		int currentCount = repository.countByReviewNo(reviewNo);
		int newCount = currentCount + increment;
		reviewRepository.findById(reviewNo).ifPresent(review -> {
			review.setLikeCnt(newCount);
			reviewRepository.save(review);
		});
	}

	// 5. 특정리뷰의 좋아요 수 카운트
	public int getReviewLikesCount(int reviewNo) {
		return repository.countByReviewNo(reviewNo);
	}


	// 장소 좋아요
	@Transactional
	public void togglePlaceLike(LikesPlaceDto dto) {
		// 4. 사용자가 특정 장소에 대해 좋아요를 눌렀는지 확인
		Optional<LikesEntity> currentLike = repository.findByUserIdAndPlaceNo(dto.getUserId(), dto.getPlaceNo());

		if (currentLike.isPresent()) {
			// 좋아요가 존재하면 삭제
			repository.deleteByUserIdAndPlaceNo(dto.getUserId(), dto.getPlaceNo());
			// 장소의 좋아요 수 감소
			updatePlaceLikeCnt(dto.getPlaceNo(), -1);
		} else {
			// 좋아요가 존재하지 않으면 추가
			LikesEntity newLike = LikesPlaceDto.toEntity(dto);
			repository.save(newLike);

			// 장소의 좋아요 수 증가
			updatePlaceLikeCnt(dto.getPlaceNo(), 1);
		}
	}

	// 4-1. 좋아요가 추가되거나 삭제될 때 호출되어 장소의 좋아요 수를 정확히 유지하도록
	private void updatePlaceLikeCnt(int placeNo, int increment) {
		int currentCount = repository.countByPlaceNo(placeNo);
		int newCount = currentCount + increment;
		placeRepository.findById(placeNo).ifPresent(place -> {
			place.setLikeCnt(newCount);
			placeRepository.save(place);
		});
	}

	// 5. 특정 장소의 좋아요 수 카운트
	public int getPlaceLikesCount(int placeNo) {
		return repository.countByPlaceNo(placeNo);
	}
}