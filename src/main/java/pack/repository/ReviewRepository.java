package pack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pack.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer>{

	//장소의 리뷰들 조회. 최신순나열
	List<ReviewEntity> findByPlaceNoOrderByCreateDateDesc(int placeNo);
	
	// 특정 장소의 리뷰 수를 카운트하는 메서드
    @Query("SELECT COUNT(r) FROM ReviewEntity r WHERE r.placeNo = :placeNo")
    int countReviewsByPlaceNo(@Param("placeNo") int placeNo);
	
	//장소의 리뷰 생성, 수정
	//jpa에서 제공하는 .save()를 사용.
	
	//리뷰 삭제
	//
	
}
