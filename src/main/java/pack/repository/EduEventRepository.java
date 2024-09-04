package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pack.entity.EduEventEntitiy;

public interface EduEventRepository extends JpaRepository<EduEventEntitiy, Integer> {
}
