package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pack.entity.FilesEntity;

import java.util.List;


public interface FilesRepository extends JpaRepository<FilesEntity,Integer> {

	//추가시 증가용) 파일 가장 큰 번호
	 @Query("select Max(f.no) from FilesEntity f")
	 Integer findbyMaxNo();

	// 특정 플리마켓 게시물 번호에 해당하는 파일 정보 가져오기
	List<FilesEntity> findByFleamarketEntity_No(Integer fleaMarketNo);

	// 특정 플리와 관련된 files삭제
	void deleteByFleamarketEntity_No(Integer fleaMarketNo);
}
