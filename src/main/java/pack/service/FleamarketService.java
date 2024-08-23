package pack.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import pack.dto.FleamarketDto;
import pack.entity.FleamarketEntity;
import pack.entity.UserEntity;
import pack.repository.FleamarketRepository;
import pack.repository.UserRepository;

@Service
public class FleamarketService {
	
	@Autowired
	private FleamarketRepository repository;
	
	@Autowired
    private UserRepository userRepository;
	
	//전체보기
//	public List<FleamarketDto> findAll() {
//		return repository.findAll().stream().map(FleamarketEntity::toDto).collect(Collectors.toList());
//	}
	
	//전체보기 (페이징 처리)
	public Page<FleamarketDto> findAll(Pageable pageable) {
	    // Pageable 객체에서 페이지 번호와 크기를 가져와서 새로운 Pageable 객체 생성
	    Pageable sortedByMnoDesc = PageRequest.of(
	        pageable.getPageNumber(), // 인스턴스 메서드를 호출하여 페이지 번호를 가져옴
	        pageable.getPageSize(), // 인스턴스 메서드를 호출하여 페이지 크기를 가져옴
	        Sort.by("mNo").descending() // mNo 필드를 기준으로 내림차순 정렬
	    );

	    return repository.findAll(sortedByMnoDesc).map(FleamarketEntity::toDto);
	}

	
	//검색(페이징 처리)
	public Page<FleamarketDto> search(String category, String input,Pageable page) {
	    // 입력값에 와일드카드를 추가 > jpql로 입력시 오류
	    String searchPattern = "%" + input + "%";
	    if (category.equals("전체")) {
	        return repository.searchByTitleOrContent(searchPattern,page)
	                .map(FleamarketEntity::toDto);
	    } 
	    return repository.searchCategory(category, searchPattern, page)
                .map(FleamarketEntity::toDto);
	}
	
	//가장 마지막게시판 번호
	public Integer maxBoardNum() {
		return repository.findbyMaxmNo();
	}
	
//	//가장 마지막파일 번호
//	public Integer maxfileNum() {
//		return repository.findbyMaxfNo();
//	}
	
	//생성
	public String insert(FleamarketDto dto) {
		try {
			 // UserEntity를 데이터베이스에서 로드
            UserEntity userEntity = userRepository.findById(dto.getMId());
            if (userEntity == null) {
                return "입력 오류 : User not found with id: " + dto.getMId();
            }

            // FleamarketEntity 생성
            FleamarketEntity insertEntity = FleamarketDto.toEntity(dto);
            insertEntity.setUserEntity(userEntity);  // 로드된 UserEntity를 설정

            // 생성 날짜 설정
            insertEntity.setMCreateDate(LocalDateTime.now());
            
            // 데이터베이스에 저장
            repository.save(insertEntity);
            return "isSuccess";
			
		} catch (Exception e) {
			return "입력 오류 : insert" +e.getMessage();
		}
	}
	
	//상세보기
	public FleamarketDto getOne(Integer no) {
		return FleamarketEntity.toDto(repository.findBymNo(no));
	}
	
	//수정
	public String putOne(FleamarketDto dto) {
		try {
			//특정 번호의 데이터 가져오기
			FleamarketEntity oneEntity= repository.findBymNo(dto.getMNo());
			//수정된 정보 넣기
			oneEntity.setMTitle(dto.getMTitle());
			oneEntity.setMPrice(dto.getMPrice());
			oneEntity.setMCont(dto.getMCont());
			oneEntity.setMUpdateDate(LocalDateTime.now());
			repository.save(oneEntity);
			
			return "isSuccess";
			
		} catch (Exception e) {
			return "입력 오류 : putOne" +e.getMessage();
		}
	}
	
	//삭제
	public String deleteOne(Integer no) {
		try {
			//특정 번호의 데이터
			FleamarketEntity entity = repository.findBymNo(no);
			repository.delete(entity);
			return "isSuccess";
			
		} catch (Exception e) {
			return "삭제 오류 : deleteOne" +e.getMessage();
		}
	}
	
}
