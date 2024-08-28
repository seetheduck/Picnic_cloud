package pack.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import pack.entity.FilesEntity;
import pack.entity.FleamarketEntity;
import pack.repository.FilesRepository;
import pack.repository.FleamarketRepository;

@Repository
@Transactional
public class FilesService {
	
	@Autowired
	private FleamarketRepository fleamarketRepository;
	
	@Autowired
	private FilesRepository repository;
	
	//게시판 등록시
	public void insertFleaFile(String path,Integer no) {
		 FleamarketEntity fleamarketEntity = fleamarketRepository.findByNo(no);
		
        // 파일 테이블에 저장
        Integer maxfileNum = repository.findbyMaxNo();
//        System.out.println("asssssssss : "+maxfileNum);
		FilesEntity entity = new FilesEntity();
		entity.setUserid(fleamarketEntity.getUserEntity().getId());
		entity.setNo(maxfileNum);
		entity.setPath(path);
		entity.setUploadDate(LocalDateTime.now());
		entity.setFleamarketEntity(fleamarketEntity);
		repository.save(entity);
	}

}
