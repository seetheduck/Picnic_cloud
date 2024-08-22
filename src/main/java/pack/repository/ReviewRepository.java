package pack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pack.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer>{

	//장소의 리뷰조회 /places/{p_no}/reviews
	@Query("select r from ReviewEntity as r where r.rPno = :pNo")
	List<ReviewEntity> find(@Param("pNo") int pNo);
	
	//장소의 리뷰생성 /places/{p_no}/reviews
	
	
	//장소의 리뷰수정 /review/{r_no}
	
	
	//장소의 리뷰 삭제 /review/{r_no}
	
}
