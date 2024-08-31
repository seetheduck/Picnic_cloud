package pack.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pack.entity.FleamarketEntity;

public interface FleamarketRepository extends JpaRepository<FleamarketEntity,Integer> {
	
	//전체 목록값 불러오기(페이징)
	Page<FleamarketEntity> findAll(Pageable page);

	//추가시 증가용) 게시판 가장 큰 번호
	@Query("select Max(f.no) from FleamarketEntity f")
	Integer findbyMaxNo();
	
	//검색)카테고리가 전체인 경우
//		List<FleamarketEntity> findByMTitleContainingOrMContContaining(String input);
	@Query("select f from FleamarketEntity f where f.title like %:input% or f.contents like %:input% order by f.no desc")
	Page<FleamarketEntity> searchByTitleOrContent(@Param("input") String input, Pageable page);
	
	//검색)카테고리 선택된 경우
	@Query("select f from FleamarketEntity f where f.categoryEntity.marketNo = :category and (f.title like concat('%', :input, '%') or f.contents like concat('%', :input, '%')) order by f.no desc")
	Page<FleamarketEntity> searchCategory(@Param("category") String category, @Param("input") String input, Pageable page);

	//특정 게시물 반환
	FleamarketEntity findByNo(Integer no);


}
