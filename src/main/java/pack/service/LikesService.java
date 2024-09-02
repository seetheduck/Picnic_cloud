package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import pack.entity.FleamarketEntity;
import pack.entity.LikesEntity;
import pack.entity.UserEntity;
import pack.repository.LikesRepository;
import pack.repository.UserRepository;
import pack.repository.FleamarketRepository;

@Service
public class LikesService {

	@Autowired
	private LikesRepository repository;

	@Autowired
	private UserRepository userRepository;

	// 좋아요 토글
	@Transactional
	public int toggleFleaMarketLike(String userId, int fleaBoardNo) {
		// UserEntity를 직접 반환하고, null이 아니라고 가정
		UserEntity userEntity = userRepository.findById(userId);
		if (userEntity == null) {
			throw new RuntimeException("User not found");
		}

		// 좋아요 상태 확인
		LikesEntity existingLike = repository.findByUserIdAndFleaMarketNo(userId, fleaBoardNo);

		if (existingLike != null) {
			// 이미 좋아요가 눌려 있으면 좋아요 취소
			repository.delete(existingLike);
		} else {
			// 좋아요 추가
			LikesEntity newLike = LikesEntity.builder()
					.no(repository.maxFavNum() + 1)
					.userId(userId) // 좋아요 누른 유저 ID 저장
					.fleaMarketNo(fleaBoardNo) // 좋아요 누른 게시물 번호 저장
					.build();
			repository.save(newLike);
		}

		// 업데이트된 좋아요 수 반환
		return repository.countByFleaMarketNo(fleaBoardNo);
	}

	// 좋아요 여부 확인
	public boolean checkLikes(String userId, Integer fleaMarketNo) {
		// UserEntity를 직접 반환하고, null이 아니라고 가정
		UserEntity userEntity = userRepository.findById(userId);
		if (userEntity == null) {
			throw new RuntimeException("User not found");
		}

		LikesEntity existingLike = repository.findByUserIdAndFleaMarketNo(userId, fleaMarketNo);
		return existingLike != null;
	}

	// 특정 게시물 좋아요 수 계산
	public int countFleaLikes(Integer fleaMarketNo) {
		return repository.countByFleaMarketNo(fleaMarketNo);
	}
}
