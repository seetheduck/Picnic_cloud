package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pack.entity.UserDetailEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Integer>{
	
	UserDetailEntity findByNo(int no);
	
	List<UserDetailEntity> findByAccountDeleteDateBefore(LocalDateTime thresholdDate);

	Optional<UserDetailEntity> findByEmail(String email);

}
