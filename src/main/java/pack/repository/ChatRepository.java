package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pack.entity.ChatEntity;


public interface ChatRepository extends JpaRepository<ChatEntity,Integer> {

}
