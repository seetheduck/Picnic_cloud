package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pack.entity.LikesEntity;

public interface LikesRepository extends JpaRepository<LikesEntity, Integer> {
	
	//좋아요 증가
	@Query("select Max(l.no) from LikesEntity l")
	Integer maxFavNum();
	
	// 특정 유저가 특정 플리마켓에 좋아요를 눌렀는지 확인
	LikesEntity findByUserEntity_IdAndFleamarketEntity_No(String userid, Integer fleaMarketNo);
	
	//좋아요 취소하기
//	int deleteByUserEntity_IdAndFleamarketEntity_MNo(String userid, Integer fleaMarketNo);
	
	// 특정 플리마켓의 좋아요 수 계산하기
    int countByFleamarketEntity_No(Integer fleaMarketNo);
}
