package pack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pack.entity.FacilitiesMapEntity;

public interface FacilitiesMapRepository extends JpaRepository<FacilitiesMapEntity, Integer>{
	
	List<FacilitiesMapEntity> findByCategory(String category);
	
}
