package pack.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pack.entity.ReviewEntity;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

    //장소의 리뷰들 조회. 최신순나열. 페이징처리.
    Page<ReviewEntity> findByPlaceNoOrderByCreateDateDesc(int placeNo, Pageable pageable);


    //장소의 리뷰 생성, 수정
    //jpa에서 제공하는 .save()를 사용.

    //리뷰 삭제

    //장소의 리뷰 수 카운트
    @Query("SELECT COUNT(r) FROM ReviewEntity r WHERE r.placeNo = :placeNo")
    int countReviewsByPlaceNo(@Param("placeNo") int placeNo);

}
