package pack.controller;

import jakarta.servlet.http.HttpServletRequest;
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
import pack.service.FleamarketService;

@RestController
@CrossOrigin("*")
public class FleamarketController {

	@Autowired
	private FleamarketService fleamarketService;

	private static final String USER_ID_ATTRIBUTE = "userId";

	//플리마켓 홈 (페이징)
	@GetMapping("/fleaMarket")
	public ResponseEntity<Page<FleamarketDto>> getListAll(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "9") int size,
			@RequestParam(value = "category") Integer category,
			@RequestParam(value = "search", required = false) String search,
			HttpServletRequest request
	) {
		Pageable pageable = PageRequest.of(page, size);

		// 현재 사용자 ID 가져오기 (비로그인 사용자의 경우 null 처리)
		String userId = null;
		if (request.getAttribute(USER_ID_ATTRIBUTE) != null) {
			userId = request.getAttribute(USER_ID_ATTRIBUTE).toString();
		}


		Page<FleamarketDto> result;

		// 카테고리와 검색 값에 따라 다른 메서드 호출
		if (category == 0 && (search == null || search.isEmpty())) {
			result = fleamarketService.getFleaMarketWithLikes(pageable, userId);  // 전체 목록
		} else {
			result = fleamarketService.searchWithLikes(category, search, pageable, userId);  // 검색 결과
		}

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
			@RequestPart(value = "file", required = false) MultipartFile file,
			HttpServletRequest request) {

		try {
			String userId = request.getAttribute(USER_ID_ATTRIBUTE).toString();

			ObjectMapper objectMapper = new ObjectMapper();
			FleamarketDto fleamarketDto = objectMapper.readValue(dtoJson, FleamarketDto.class);

			fleamarketDto.setUserId(userId);

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
	public ResponseEntity<String> putOne(
			@PathVariable("no") Integer no,
			@RequestBody FleamarketDto dto,
			HttpServletRequest request) {
		try {
			// 요청에서 userId 추출
			String userId = request.getAttribute("userId").toString();

			dto.setUserId(userId);
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
	public ResponseEntity<String> deleteOne(@PathVariable("no") Integer no,HttpServletRequest request) {
		try {
			String userId = request.getAttribute("userId").toString();
			// 해당 번호의 게시물 정보 가져오기
			FleamarketDto fleamarketDto = fleamarketService.getOne(no);

			// 게시물의 작성자와 요청한 사용자가 일치하는지 확인
			if (!fleamarketDto.getUserId().equals(userId)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body("You do not have permission to delete this FleaMarket post");
			}
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
