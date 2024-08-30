package pack.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pack.dto.LikesReviewDto;
import pack.entity.LikesReviewEntity;
import pack.repository.LikesReviewRepository;

@Service
public class LikesReviewService {//좋아요 관련 작업
	@Autowired
	private LikesReviewRepository likesReviewRepository;

	// 좋아요 토글처리
	@Transactional
	public void toggleLike(LikesReviewDto dto) {
		Optional<LikesReviewEntity> currentLike = likesReviewRepository.findByUserIdAndPlaceNo(dto.getUserId(),
				dto.getPlaceNo());

		if (currentLike.isPresent()) {
			// 좋아요가 존재하면 삭제
			likesReviewRepository.deleteByUserIdAndPlaceNo(dto.getUserId(), dto.getPlaceNo());
		} else {
			// 좋아요가 존재하지 않으면 추가
			LikesReviewEntity newLike = LikesReviewDto.toLikesReviewEntity(dto);
			likesReviewRepository.save(newLike);

		}

	}

	// 특정리뷰의 좋아요 수 카운트
	public int getLikesCount(int placeNo) {
		return likesReviewRepository.countByReviewNo(placeNo);
	}
	
	
}
