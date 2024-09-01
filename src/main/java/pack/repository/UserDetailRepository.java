package pack.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.entity.UserDetailEntity;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Integer>{
	
	UserDetailEntity findByNo(int no);
	
	List<UserDetailEntity> findByAccountDeleteDateBefore(LocalDateTime thresholdDate);
	
}
