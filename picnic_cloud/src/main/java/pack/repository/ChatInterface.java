package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pack.entity.ChatEntity;


public interface ChatInterface extends JpaRepository<ChatEntity,Integer> {

}
