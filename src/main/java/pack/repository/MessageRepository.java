package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import pack.entity.MessageEntity;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

    //메시지 no 최대값
    @Query("SELECT MAX(m.no) FROM MessageEntity m")
    Integer findMaxMessageNo();

    // 특정 채팅방의 메시지 조회
    List<MessageEntity> findByChatRoomEntityNo(Integer chatRoomNo);
}
