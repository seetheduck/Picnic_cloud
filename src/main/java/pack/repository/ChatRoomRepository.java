package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pack.entity.ChatRoomEntity;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity,Integer> {

    //채팅방 no 최대값
    @Query("SELECT MAX(c.no) FROM ChatRoomEntity c")
    Integer findMaxChatRoomNo();

}
