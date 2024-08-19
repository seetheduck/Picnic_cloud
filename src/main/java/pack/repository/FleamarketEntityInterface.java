package pack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pack.entity.FleamarketEntity;


public interface FleamarketEntityInterface extends JpaRepository<FleamarketEntity,Integer> {

	//findbyMaxNum 자동증가용
	//int findbyMaxNum();

	//검색)카테고리가 전체인 경우
//	List<FleamarketEntity> findByMTitleContainingOrMContContaining(String input);
	@Query("select f from FleamarketEntity f where f.mTitle like %:input% or f.mCont like %:input%")
	List<FleamarketEntity> searchBTitleOrContent(@Param("input") String input);

	
	//검색)카테고리 선택된 경우
//	List<FleamarketEntity> findBymCategoryAndmTitleOrmConContaining(String category,String input);
	@Query("select f from FleamarketEntity f where f.mCategory = :category and (f.mTitle like %:input% or f.mCont like %:input%)")
	List<FleamarketEntity> searchCategory(@Param("category") String category,@Param("input") String input);
	
	//특정 게시물 반환
	FleamarketEntity findBymNo(Integer mNo);
	
}
