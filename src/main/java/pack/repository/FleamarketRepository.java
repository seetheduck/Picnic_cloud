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

	//추가시 증가용) 게시판 가장 큰 번호 //FleamarketEntity findTopByOrderByMNoDesc();
	@Query("select Max(f.mNo) from FleamarketEntity f ")
	Integer findbyMaxmNo();
	
	//검색)카테고리가 전체인 경우
//		List<FleamarketEntity> findByMTitleContainingOrMContContaining(String input);
	@Query("select f from FleamarketEntity f where f.mTitle like %:input% or f.mCont like %:input% order by f.mNo desc")
	Page<FleamarketEntity> searchByTitleOrContent(@Param("input") String input, Pageable page);
	
	//검색)카테고리 선택된 경우
//		List<FleamarketEntity> findBymCategoryAndmTitleOrmConContaining(String category,String input);
	@Query("select f from FleamarketEntity f where f.mCategory = :category and (f.mTitle like :input or f.mCont like :input) order by f.mNo desc")
	Page<FleamarketEntity> searchCategory(@Param("category") String category, @Param("input") String input, Pageable page);
	
	//특정 게시물 반환
	FleamarketEntity findBymNo(Integer mNo);
	
	//@Modifying(clearAutomatically = true)
	// 상세보기 할 때 조회수 증가
	// @Modifying은 @Query로 작성된 INSERT, UPDATE, DELETE 쿼리를 사용
	
}
