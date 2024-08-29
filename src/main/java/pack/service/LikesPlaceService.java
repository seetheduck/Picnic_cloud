package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pack.entity.LikesPlaceEntity;
import pack.repository.LikesPlaceRepository;

@Service
public class LikesPlaceService {
	@Autowired
	private LikesPlaceRepository likesPlaceRepository;
	
	// 장소에 대한 좋아요 수를 가져오는 메소드
    public int getLikesCountByPlaceNo(Integer placeNo) {
        return likesPlaceRepository.countLikesByPlaceNo(placeNo)
        		.map(LikesPlaceEntity::toLikesPlacesDto);
    }
    
    //좋아요 토글메소드 
    //@Transactional을 쓰는 이유: 토글=좋아요를 추가/삭제하는 작업. 하나의 단위로 묶여야 데이터의 일관성을 유지 가능.
    @Transactional
    public long toggleLike(Integer placeNo, String userId) {
        LikesPlaceEntity currentLike = likesPlaceRepository.findByPlaceNoAndUserId(placeNo, userId);
        
        if (currentLike != null) {
            // 좋아요 눌린경우, 좋아요 해제. 
            likesPlaceRepository.delete(currentLike);
        } else {
            // 좋아요 안 눌린경우, 좋아요 추가.
            LikesPlaceEntity newLike = LikesPlaceEntity.builder()
                .userId(userId)
                .placeNo(placeNo)
                .build();
            likesPlaceRepository.save(newLike);
        }

        // 토글변경사항을 반영
        return likesPlaceRepository.countLikesByPlaceNo(placeNo);
    }
}
