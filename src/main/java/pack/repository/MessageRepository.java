package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pack.entity.MessageEntity;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

}
