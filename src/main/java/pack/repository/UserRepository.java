package pack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.entity.UserMasterEntity;

@Repository
public interface UserRepository extends JpaRepository<UserMasterEntity, Integer> {
	
	Optional<UserMasterEntity> findById(String id);
	
	@Query("SELECT COALESCE(MAX(u.no), 0) FROM UserMasterEntity u")
    int findMaxNo();
	
	
	
}