package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pack.entity.PlaceEntity;

@Repository
public interface MyPageRepository extends JpaRepository<PlaceEntity, Integer>, MyPageRepositoryCustom {
    // 커스텀 쿼리 메서드는 MyPageRepositoryCustom에서 정의
}
