package pack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pack.entity.ChatRoomListEntity;

public interface ChatRoomListRepository extends JpaRepository<ChatRoomListEntity, Integer> {
    
	// 사용자 ID로 채팅방 목록을 조회
    List<ChatRoomListEntity> findByUserId(Integer userId);
}
