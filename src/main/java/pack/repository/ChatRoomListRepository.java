package pack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import pack.entity.ChatRoomListEntity;

public interface ChatRoomListRepository extends JpaRepository<ChatRoomListEntity, Integer> {

    @Query("SELECT COALESCE(MAX(c.no), 0) FROM ChatRoomListEntity c")
    int findMaxNo();

	// 사용자 ID로 채팅방 목록을 조회
    List<ChatRoomListEntity> findByUserId(String userId);

    //해당 채팅방이 있는지 없는지 유무
    boolean existsByUserIdAndChatRoomNo(String userId, Integer chatRoomNo);
}
