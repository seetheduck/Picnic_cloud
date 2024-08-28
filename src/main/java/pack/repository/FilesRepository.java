package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pack.entity.FilesEntity;


public interface FilesRepository extends JpaRepository<FilesEntity,Integer> {

	//추가시 증가용) 파일 가장 큰 번호
	 @Query("select Max(f.no) from FilesEntity f")
	 Integer findbyMaxNo();
	
}
