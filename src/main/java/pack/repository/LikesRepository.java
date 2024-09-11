package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pack.entity.FleamarketEntity;
import pack.entity.LikesEntity;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<LikesEntity, Integer> {

	// 좋아요 증가
	@Query("select Max(l.no) from LikesEntity l")
	Integer maxFavNum();

	// 특정 유저가 특정 플리마켓에 좋아요를 눌렀는지 확인
	LikesEntity findByUserIdAndFleaMarketNo(String userId, Integer fleaMarketNo);

	// 특정 플리마켓의 좋아요 수 계산하기
	int countByFleaMarketNo(Integer fleaMarketNo);

	//리뷰글의 좋아요 로직
	//1. 리뷰에 대한 좋아요 토글 처리
	//사용자가 특정 리뷰에 대해 이미 좋아요를 눌렀는지를 확인
	Optional<LikesEntity> findByUserIdAndReviewNo(String userId, Integer reviewNo);

	//좋아요가 존재하면 존재하는 좋아요를 삭제
	void deleteByUserIdAndReviewNo(String userId, Integer reviewNo);
	//존재하는 좋아요가 없으면, .save()를 통해 수행

	//2. 리뷰에 대한 좋아요 수 카운트 기능//쿼리문으로
	@Query("SELECT COUNT(l) FROM LikesEntity l WHERE l.reviewNo = :reviewNo")
	int countByReviewNo(@Param("reviewNo") int reviewNo);

	//장소의 좋아요 로직
	//1. 장소에 대한 좋아요 토글 처리
	//사용자가 특정 리뷰에 대해 이미 좋아요를 눌렀는지를 확인
	Optional<LikesEntity> findByUserIdAndPlaceNo(String userId, Integer placeNo);

	//좋아요가 존재하면 존재하는 좋아요를 삭제
	void deleteByUserIdAndPlaceNo(String userId, Integer placeNo);
	//존재하는 좋아요가 없으면, .save()를 통해 수행

	//2. 장소에 대한 좋아요 수 카운트 기능
	int countByPlaceNo(int placeNo);

	@Query("SELECT l.fleaMarketNo FROM LikesEntity l WHERE l.userId = :userId")
	List<Integer> findFleaMarketNosByUserId(@Param("userId") String userId);

	@Query("SELECT l FROM LikesEntity l WHERE l.userId = :userId AND l.fleaMarketNo IS NOT NULL")
	List<LikesEntity> findLikedFleaMarketsByUserId(@Param("userId") String userId);

}
