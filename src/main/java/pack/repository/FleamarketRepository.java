package pack.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pack.entity.FleamarketEntity;

import java.util.List;

public interface FleamarketRepository extends JpaRepository<FleamarketEntity,Integer>, FleamarketRepositoryCustom {

	//추가시 증가용) 게시판 가장 큰 번호
	@Query("select Max(f.no) from FleamarketEntity f")
	Integer findbyMaxNo();
//
//	// 검색)카테고리 선택된 경우
//	@Query("select f from FleamarketEntity f " +
//			"where (:category = 0 or f.categoryEntity.no = :category) " +
//			"and (:input is null or f.title like %:input% or f.contents like %:input%) " +
//			"order by f.no desc")
//	Page<FleamarketEntity> searchCategory(
//			@Param("category") Integer category, @Param("input") String input, Pageable page);

	//특정 게시물 반환
	FleamarketEntity findByNo(Integer no);

	// 사용자 게시글 조회
	@Query("SELECT f FROM FleamarketEntity f WHERE f.userEntity.id = :userId")
	Page<FleamarketEntity> findUserPostsByUserId(@Param("userId") String userId, Pageable pageable);

	@Query("SELECT f FROM FleamarketEntity f JOIN LikesEntity l ON f.no = l.fleaMarketNo WHERE l.userId = :userId")
	List<FleamarketEntity> findUserLikedFleamarketPosts(@Param("userId") String userId);

	Page<FleamarketEntity> findAllByNoIn(List<Integer> noList, Pageable pageable);

}
