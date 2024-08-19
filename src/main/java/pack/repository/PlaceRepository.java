package pack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pack.entity.PlaceEntity;

public interface PlaceRepository extends JpaRepository<PlaceEntity, Integer>{
	
	//카테고리별 장소. 평점이 높은순으로, 같은 평점내에선 좋아요 수가 많은순으로. 
	@Query("select p from PlaceEntity as p where p.pCategory = :pCategory order by p.point desc, p.like_cnt desc")
	List<PlaceEntity> findBypCategory(@Param("pCategory") String pCategory);
	
	//장소 키워드검색

}
