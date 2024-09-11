package pack.service;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pack.dto.FilesDto;
import pack.dto.FleamarketDto;
import pack.entity.FleamarketEntity;
import pack.entity.UserEntity;
import pack.repository.*;

@Service
public class FleamarketService {

	@Autowired
	private FleamarketRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FilesService filesService;

	@Autowired
	private FilesRepository filesRepository;

	@Autowired
	private LikesRepository likesRepository;

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private LikesService likesService;

	// 전체 플리마켓 게시물 리스트 가져오기 (좋아요 및 파일 정보 포함)
	public Page<FleamarketDto> getFleaMarketWithLikes(Pageable pageable, String userId) {
		Page<FleamarketDto> fleaMarkets = repository.findAll(pageable)
				.map(FleamarketEntity::toDto);

		// 각 플리마켓 게시물에 대해 좋아요 상태 및 파일 경로 추가
		fleaMarkets.forEach(fleaMarketDto -> {
			int fleaMarketNo = fleaMarketDto.getNo();

			// 좋아요 정보 추가
			addLikeInfo(fleaMarketDto, userId);

			// 파일 경로 추가
			addFileInfo(fleaMarketDto, fleaMarketNo);
		});

		return fleaMarkets;
	}
	//검색 결과 (좋아요 포함)
	public Page<FleamarketDto> searchWithLikes(Integer category, String search, Pageable pageable, String userId) {
		Page<FleamarketDto> fleaMarkets =  repository.searchCategory(category, search, pageable)
				.map(FleamarketEntity::toDto);

		// 각 플리마켓 게시물에 대해 좋아요 상태 및 파일 경로 추가
		fleaMarkets.forEach(fleaMarketDto -> {
			int fleaMarketNo = fleaMarketDto.getNo();

			// 좋아요 정보 추가
			addLikeInfo(fleaMarketDto, userId);

			// 파일 경로 추가
			addFileInfo(fleaMarketDto, fleaMarketNo);
		});

		return fleaMarkets;
	}

	// 특정 플리마켓 게시물에 파일 정보 추가
	private void addFileInfo(FleamarketDto fleaMarketDto, int fleaMarketNo) {
		List<String> filePaths = filesService.getFilePathsByFleaMarketNo(fleaMarketNo);
		fleaMarketDto.setFiles(filePaths);
	}

	// 특정 플리마켓 게시물에 좋아요 정보 추가
	private void addLikeInfo(FleamarketDto fleaMarketDto, String userId) {
		int fleaMarketNo = fleaMarketDto.getNo();
		int favoriteCnt = likesService.countFleaLikes(fleaMarketNo);
		boolean isFavorite = userId != null && likesService.checkLikes(userId, fleaMarketNo);

		fleaMarketDto.setFavorite(isFavorite);
		fleaMarketDto.setFavoriteCnt(favoriteCnt);
	}

	//생성
	@Transactional
	public String insert(FleamarketDto dto,MultipartFile file) {
		try {
			// UserEntity를 데이터베이스에서 로드
			UserEntity userEntity = userRepository.findById(dto.getUserId())
					.orElseThrow(() -> new IllegalArgumentException("User not found with id: " + dto.getUserId()));

			// FleamarketEntity 생성
			FleamarketEntity insertEntity = FleamarketDto.toEntity(dto);
			insertEntity.setUserEntity(userEntity);  // 로드된 UserEntity를 설정

			//마지막 번호 + 1
			int newNum = repository.findbyMaxNo() + 1;
			insertEntity.setNo(newNum);

			// 생성 날짜 설정
			insertEntity.setCreatedate(LocalDateTime.now());

			// 데이터베이스에 저장
			repository.save(insertEntity);

			//파일 저장 영역
			filesService.insertFleaFile(newNum,file);

			return "isSuccess";

		} catch (Exception e) {
			return "입력 오류 : insert" +e.getMessage();
		}
	}

	//상세보기
	public FleamarketDto getOne(Integer no) {
		FleamarketEntity entity = repository.findByNo(no);
		if (entity != null) {
			FleamarketDto dto = entity.toDto();
			// 파일 정보 가져오기
			List<String> files = filesService.getFilePathsByFleaMarketNo(no);
			dto.setFiles(files);
			return dto;
		}
		return null; // 엔티티가 없는 경우 null 반환
	}

	//수정
	public String putOne(FleamarketDto dto) {
		try {
			//특정 번호의 데이터 가져오기
			FleamarketEntity oneEntity = repository.findByNo(dto.getNo());
			//수정된 정보 넣기
			oneEntity.setTitle(dto.getTitle());
			oneEntity.setPrice(dto.getPrice());
			oneEntity.setContents(dto.getContents());
			oneEntity.setUpdatedate(LocalDateTime.now());
			repository.save(oneEntity);

			return "isSuccess";

		} catch (Exception e) {
			return "입력 오류 : putOne" +e.getMessage();
		}
	}

	//삭제
	@Transactional
	public String deleteOne(int no) {
		try {
			// 'report' 테이블의 관련 레코드 삭제
			reportRepository.deleteByFleaMarketNo(no);
			// 'files' 테이블의 관련 레코드 삭제
			filesRepository.deleteByFleamarketEntity_No(no);
			// 'likes' 테이블의 관련 레코드 삭제
			likesRepository.deleteByFleaMarketNo(no);
			//특정 번호의 데이터
			FleamarketEntity entity = repository.findByNo(no);
			repository.delete(entity);
			return "isSuccess";

		} catch (Exception e) {
			return "삭제 오류 : deleteOne" +e.getMessage();
		}
	}

	// no 최대값 카운트
	public Integer maxBoardNum() {
		return repository.findbyMaxNo();
	}
}