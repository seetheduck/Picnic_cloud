package pack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	UserEntity findById(String id);

	@Query("SELECT COALESCE(MAX(u.no), 0) FROM UserEntity u")
    int findMaxNo();
	
	boolean existsById(String id);
	
}