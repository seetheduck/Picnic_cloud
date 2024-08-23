package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pack.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    // UserEntity 조회 메서드
	@Query("SELECT u FROM UserEntity u WHERE u.id = :id")
    UserEntity findById(@Param("id") String id);
}