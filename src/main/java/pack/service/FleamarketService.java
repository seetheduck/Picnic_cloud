package pack.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pack.dto.FleamarketDto;
import pack.entity.FleamarketEntity;
import pack.repository.FleamarketEntityRepository;

@Service
public class FleamarketService {
	
	@Autowired
	private FleamarketEntityRepository repository;
	
	//전체보기
	public List<FleamarketDto> findAll() {
		return repository.findAll().stream().map(FleamarketEntity::toDto).collect(Collectors.toList());
	}
	
	//검색
	public List<FleamarketDto> search(String category, String input) {
	    // 입력값에 와일드카드를 추가
	    String searchPattern = "%" + input + "%";

	    if (category.equals("전체")) {
	        return repository.searchByTitleOrContent(searchPattern)
	                .stream()
	                .map(FleamarketEntity::toDto)
	                .collect(Collectors.toList());
	    }
	    return repository.searchCategory(category, searchPattern)
	            .stream()
	            .map(FleamarketEntity::toDto)
	            .collect(Collectors.toList());
	}

	
	//생성
	public String insert(FleamarketDto dto) {
		try {
			FleamarketEntity insertEntity = FleamarketDto.toEntity(dto);
			insertEntity.setMCreateDate(LocalDateTime.now());//현재 시간
			repository.save(insertEntity);
			return "success";
			
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
			
			return "success";
			
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
