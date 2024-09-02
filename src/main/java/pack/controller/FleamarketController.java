package pack.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import pack.dto.FleamarketDto;
import pack.entity.FleamarketEntity;
import pack.entity.UserEntity;
import pack.repository.UserRepository;
import pack.service.FilesService;
import pack.service.FleamarketService;
import pack.service.UserService;

@RestController
@CrossOrigin("*")
public class FleamarketController {
	@Autowired
	private FleamarketService fleamarketService;

	//플리마켓 홈 (페이징)
	@GetMapping("/fleaMarket")
	public ResponseEntity<Page<FleamarketDto>> getListAll(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "9") int size,
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "search", required = false) String search
	) {

		// 페이지 요청 생성 (* import org.springframework.data.domain.PageRequest)
		// For a stable JSON structure, please use Spring Data's PagedModel ..와 같은 메시지가 뜬다.
		// 페이징된 데이터를 반환할 때, JSON 구조가 일관되지 않거나 예상치 못한 방식으로 나타남. 일관된 json 형식으로 만드는게 중요하다.
		// Spring에서는 PagedModel 또는 PagedResourcesAssembler와 같은 도구를 제공

		Pageable pageable = PageRequest.of(page, size);

		Page<FleamarketDto> result;
		if (category == null || category.equals("전체") && (search == null || search.isEmpty())) {
			result = fleamarketService.findAll(pageable);
		} else {
			result = fleamarketService.search(category, search, pageable);
		}

		// 결과를 ResponseEntity로 반환
		return ResponseEntity.ok(result);
	}

	//상세보기
	@GetMapping("/fleaMarket/{no}")
	public FleamarketDto getOne(@PathVariable("no") Integer no) {
		return fleamarketService.getOne(no);
	}

	//추가
	@PostMapping("/fleaMarket")
	public ResponseEntity<String> postOne(
			@RequestPart("dto") String dtoJson,
			@RequestPart(value = "file", required = false) MultipartFile file) {

		try {

			ObjectMapper objectMapper = new ObjectMapper();
			FleamarketDto fleamarketDto = objectMapper.readValue(dtoJson, FleamarketDto.class);

			// 서비스 호출
			fleamarketService.insert(fleamarketDto, file);

			// 성공 시 200 OK와 메시지 반환
			return ResponseEntity.ok("FleaMarket board successfully added");
		} catch (Exception e) {
			e.printStackTrace();

			// 실패 시 500 Internal Server Error와 에러 메시지 반환
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Server error occurred: " + e.getMessage());
		}
	}

	//수정
	@PutMapping("/fleaMarket/{no}")
	public ResponseEntity<String> putOne(@PathVariable("no") Integer no, @RequestBody FleamarketDto dto) {
		try {
			dto.setNo(no);
			fleamarketService.putOne(dto);

			// 성공 시 200 OK와 메시지 반환
			return ResponseEntity.ok("FleaMarket successfully updated");
		} catch (Exception e) {
			e.printStackTrace();
			// 실패 시 500 Internal Server Error와 에러 메시지 반환
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to update FleaMarket: " + e.getMessage());
		}
	}


	//삭제
	@DeleteMapping("/fleaMarket/{no}")
	public ResponseEntity<String> deleteOne(@PathVariable("no") Integer no) {
		try {
			// 서비스 호출을 통해 삭제 수행
			fleamarketService.deleteOne(no);
			// 성공 시 200 OK와 메시지 반환
			return ResponseEntity.ok("FleaMarket successfully deleted");
		} catch (Exception e) {
			// 실패 시 500 Internal Server Error와 에러 메시지 반환
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to delete FleaMarket: " + e.getMessage());
		}
	}
}
