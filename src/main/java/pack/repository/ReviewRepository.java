package pack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pack.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer>{

	//장소의 리뷰들 조회
	List<ReviewEntity> findByrPno(int rPno);
	
	//장소의 리뷰 생성, 수정
	//jpa에서 제공하는 .save()를 사용
	
	//리뷰 삭제
	
}
