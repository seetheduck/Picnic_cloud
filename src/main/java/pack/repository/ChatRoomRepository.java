package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pack.entity.ChatRoomEntity;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity,Integer> {

}
