package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.entity.ChatRoomEntity;

public interface ChatRepository extends JpaRepository<ChatRoomEntity,Integer> {

}
