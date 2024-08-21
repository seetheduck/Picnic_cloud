package pack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pack.entity.PlaceEntity;

public interface PlaceRepository extends JpaRepository<PlaceEntity, Integer>{
	
	@Query("select p from PlaceEntity as p where p.pCategory = :pCategory "//테마별 장소
			+ "and (:keyword is null or :keyword = '' " //키워드없을수도 있다.
			+ "or p.pName like concat('%', :keyword, '%') " //검색키워드가있다면 시설명, 주소에서 필터링
			+ "or p.pAddress like concat('%', :keyword, '%')) "
			+ "order by p.pPoint desc, p.pLikeCnt desc") //평점순, 같은평점일경우 좋아요 순
	List<PlaceEntity> findBypCategoryandKeyword(@Param("pCategory") String pCategory, 
													@Param("keyword") String keyword);


}
