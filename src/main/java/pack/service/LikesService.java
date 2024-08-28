package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pack.entity.FleamarketEntity;
import pack.entity.LikesEntity;
import pack.entity.UserEntity;
import pack.repository.LikesRepository;

@Service
public class LikesService {
	
	@Autowired
	LikesRepository repository;
	
	//좋아요 토글
	public int toggleFleaMarketLike(String userid, int fleaBoardNo) {
        String userIdString = String.valueOf(userid); // userNo를 String 타입으로 변환
        LikesEntity existingLike = repository.findByUserEntity_IdAndFleamarketEntity_No(userIdString, fleaBoardNo);

        if (existingLike != null) {
            // 이미 좋아요가 눌려 있으면 좋아요 취소
            repository.delete(existingLike);
        } else {
            // 좋아요 추가
            LikesEntity newLike = LikesEntity.builder()
                    .userEntity(UserEntity.builder().id(userIdString).build()) // 좋아요 누른 유저 등록
                    .fleamarketEntity(FleamarketEntity.builder().no(fleaBoardNo).build())//좋아요 누른 게시물
                    .build();
            repository.save(newLike);
        }

        // 업데이트된 좋아요 수 반환
        return repository.countByFleamarketEntity_No(fleaBoardNo);
    }
	
	//좋아요 여부
	public boolean checkLikes(String userid, Integer fleaMarketNo) {
		String userIdString = String.valueOf(userid);
        LikesEntity existingLike = repository.findByUserEntity_IdAndFleamarketEntity_No(userIdString, fleaMarketNo);
        
		return existingLike != null;
	}
	
	//특정 게시물 좋아요 수
	public int countFleaLikes(Integer fleaMarketNo) {
		return repository.countByFleamarketEntity_No(fleaMarketNo);
	}
	
}
