package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pack.entity.LikesPlaceEntity;

public interface LikesPlaceRepository extends JpaRepository<LikesPlaceEntity, Integer>{

	//장소에 따라 좋아요 수를 카운트
	@Query("SELECT COUNT(lp) FROM LikesPlaceEntity lp WHERE lp.placeNo = :placeNo")
    long countLikesByPlaceNo(@Param("placeNo") Integer placeNo);
	
	// 장소번호와 사용자id에 따라 좋아요 항목을 조회: 좋아요 토글기능
    LikesPlaceEntity findByPlaceNoAndUserId(Integer placeNo, String userId);
}
