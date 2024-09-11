package pack.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(FilesService.class);

	@Autowired
	private FleamarketRepository fleamarketRepository;

	@Autowired
	private FilesRepository repository;

	//게시판 등록시
	public String insertFleaFile(Integer newfleaNum, MultipartFile file) {
		FleamarketEntity fleamarketEntity = fleamarketRepository.findByNo(newfleaNum);
		String path = null;
		try {
			//파일 저장 경로
			String staticDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images/";// 절대 경로 사용

			//파일명 처리
			String originalFilename = file.getOriginalFilename();
			if (originalFilename == null) {
				throw new IOException("파일명은 null일 수 없습니다.");
			}
			String safeFilename = originalFilename
					.replaceAll(" ", "_")
					.replaceAll("[^a-zA-Z0-9_\\.]", "");
			Path imagePath = Paths.get(staticDirectory, safeFilename);

			//이미지 저장 디렉토리 체크 및 생성
			File dest = imagePath.toFile();
			if (!dest.getParentFile().exists()) {
				boolean dirsCreated = dest.getParentFile().mkdirs();
				if (!dirsCreated) {
					throw new IOException("디렉토리를 생성할 수 없습니다: " + dest.getParentFile().getAbsolutePath());
				}
			}

			//파일 저장
			path = "/image/flea/" + safeFilename;
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
			logger.error("파일 저장 중 오류 발생", e);
		}
		return path;
	}

	// 특정 플리마켓 게시물 번호에 해당하는 파일 경로 가져오기
	public List<String> getFilePathsByFleaMarketNo(Integer fleaMarketNo) {
		List<FilesEntity> files = repository.findByFleamarketEntity_No(fleaMarketNo);
		return files.stream()
				.map(FilesEntity::getPath)
				.collect(Collectors.toList());
	}
}
