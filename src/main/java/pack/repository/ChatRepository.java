package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.entity.ChatEntity;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity,Integer> {

}
