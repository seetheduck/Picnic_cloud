package pack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pack.entity.LikesReviewEntity;

public interface LikesReviewRepository extends JpaRepository<LikesReviewEntity, Integer>{

	//1. 리뷰에 대한 좋아요 수 카운트 기능
	int countByReviewNo(int placeNo);
	
	//2. 리뷰에 대한 좋아요 토글 처리
	//사용자가 특정 리뷰에 대해 이미 좋아요를 눌렀는지를 확인
	Optional<LikesReviewEntity> findByUserIdAndPlaceNo(String userId, Integer placeNo);
	//좋아요가 존재하면 존재하는 좋아요를 삭제
	void deleteByUserIdAndPlaceNo(String userId, Integer placeNo);
	//존재하는 좋아요가 없으면, .save()를 통해 수행

	 
}
