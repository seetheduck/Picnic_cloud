package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pack.entity.FleamarketEntity;
import pack.entity.LikesEntity;
import pack.entity.UserEntity;
import pack.repository.LikesRepository;
import pack.repository.UserRepository;

@Service
public class LikesService {

	@Autowired
	LikesRepository repository;

	@Autowired
	UserRepository userRepository;

	// 좋아요 토글
	@Transactional
	public int toggleFleaMarketLike(String userid, int fleaBoardNo) {
//		System.out.println("********** "+fleaBoardNo);
		UserEntity userEntity = userRepository.findById(userid); //해당 아이디가 있는지 ;
		//좋아요 상태 확인
		LikesEntity existingLike = repository.findByUserEntity_IdAndFleamarketEntity_No(userEntity.getId(),
				fleaBoardNo);

		if (existingLike != null) {
			// 이미 좋아요가 눌려 있으면 좋아요 취소
			repository.delete(existingLike);
		} else {
			// 좋아요 추가
			LikesEntity newLike = LikesEntity.builder()
					.no(repository.maxFavNum() + 1)
					.userEntity(userEntity) // 좋아요 누른 유저 등록
					.fleamarketEntity(FleamarketEntity.builder().no(fleaBoardNo).build())// 좋아요 누른 게시물
					.build();
			repository.save(newLike);
		}

		// 업데이트된 좋아요 수 반환
		return repository.countByFleamarketEntity_No(fleaBoardNo);
	}

	// 좋아요 여부
	public boolean checkLikes(String userid, Integer fleaMarketNo) {
		UserEntity userEntity = userRepository.findById(userid); //해당 아이디가 있는지 검사
		LikesEntity existingLike = repository.findByUserEntity_IdAndFleamarketEntity_No(userEntity.getId(),
				fleaMarketNo);

		return existingLike != null;
	}

	// 특정 게시물 좋아요 수
	public int countFleaLikes(Integer fleaMarketNo) {
		return repository.countByFleamarketEntity_No(fleaMarketNo);
	}

}
