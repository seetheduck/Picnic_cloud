package pack.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

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
	public String insertFleaFile(Integer newfleaNum,MultipartFile file) {
		FleamarketEntity fleamarketEntity = fleamarketRepository.findByNo(newfleaNum);
		String path = null;
		try {
			//파일 저장 경로
			String staticDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images/";

			//파일명)공백을 언더바로 대체하고, URL 인코딩된 문자들을 제거
			String safeFilename = file.getOriginalFilename().replaceAll(" ", "_").replaceAll("[^a-zA-Z0-9_\\.]", "");
			Path imagePath = Paths.get(staticDirectory, safeFilename);

			//이미지 저장 디렉토리 체크 및 생성
			File dest = imagePath.toFile();
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();
			}

			// 파일을 저장하기 전에 미리 데이터베이스에 경로 설정
			path = "/images/" + safeFilename;
//		        FleamarketDto.setFilePath(path);
			//파일 저장
			file.transferTo(dest);

			// 파일 테이블에 저장
			FilesEntity entity = FilesEntity.builder()
					.no(repository.findbyMaxNo() + 1)
					.userId(fleamarketEntity.getUserEntity().getId())
					.path(path)
					.uploadDate(LocalDateTime.now())
					.fleamarketEntity(fleamarketEntity)  // 단방향 조인 설정
					.build();
			repository.save(entity);

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("file IOException : " + e);
		}
		return path;
	}
}
