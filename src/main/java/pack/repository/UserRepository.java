package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pack.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

//		@Query("SELECT u FROM UserEntity u WHERE u.id = :id")
	    UserEntity findById(String id);
}