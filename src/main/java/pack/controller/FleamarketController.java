package pack.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	private FleamarketService service;
	
	//플리마켓 홈 (페이징)
	@GetMapping("/fleaMarket")
	public ResponseEntity<Page<FleamarketDto>> getListAll(
			@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "9") int size,
			@RequestParam(value ="category", required = false) Integer category,
			@RequestParam(value="search", required = false) String search
			) {
		
		// 페이지 요청 생성 (* import org.springframework.data.domain.PageRequest)
		// For a stable JSON structure, please use Spring Data's PagedModel ..와 같은 메시지가 뜬다.
		// 페이징된 데이터를 반환할 때, JSON 구조가 일관되지 않거나 예상치 못한 방식으로 나타남. 일관된 json 형식으로 만드는게 중요하다.
		// Spring에서는 PagedModel 또는 PagedResourcesAssembler와 같은 도구를 제공
		
		Pageable pageable = PageRequest.of(page, size); 
		
		Page<FleamarketDto> result;
		if(category == null || category.equals("전체") && (search == null || search.isEmpty())) {
			result = service.findAll(pageable);
		} else {
		    result = service.search(category, search, pageable);
		}
		    
	    // 결과를 ResponseEntity로 반환
	    return ResponseEntity.ok(result);
	}
	
	//상세보기
	@GetMapping("/fleaMarket/{no}")
	public FleamarketDto getOne(@PathVariable("no") Integer no
			) {
		return service.getOne(no);
	}
	
	//생성하기
	   @PostMapping("/fleaMarket")
	   public Map<String, Object> postOne(
	           @RequestPart("dto") String dtoJson,
	           @RequestPart(value = "file", required = false) MultipartFile file) {
	       Map<String, Object> response = new HashMap<>(); //응답 데이터를 답을 맵 객체 생성

	       try {
	           ObjectMapper objectMapper = new ObjectMapper();
	           FleamarketDto fleamarketDto = objectMapper.readValue(dtoJson, FleamarketDto.class);

	           String result = service.insert(fleamarketDto,file);
	           response.put(result, true);
	           response.put("message", "FleaMarket board successfully added");
	       } catch (Exception e) {
	           e.printStackTrace();
	           response.put("isSuccess", false);
	           response.put("message", "Server error occurred: " + e.getMessage());
	       }
	       return response;
	   }

	//수정
	@PutMapping("/fleaMarket/{no}")
	public Map<String,Object> putOne(@PathVariable("no") Integer no,@RequestBody FleamarketDto dto) {
		Map<String,Object> updatemap = new HashMap<String, Object>();
		try {
			dto.setNo(no);
			String putResult= service.putOne(dto);
			updatemap.put(putResult, true);
			updatemap.put("message", "fleamarket successfully updated");
	    } catch (Exception e) {
	    	updatemap.put("isSuccess", false);
	    	updatemap.put("message", "Failed to update fleamarket");
	    	updatemap.put("error", e.getMessage());
	    }
		return updatemap;
	}
	
	//삭제
	@DeleteMapping("fleaMarket/{no}")
	public Map<String,Object> deleteOne(@PathVariable("no") Integer no) {
		Map<String,Object> deletemap = new HashMap<String, Object>();
		try {
			String deleteResult= service.deleteOne(no);
			deletemap.put(deleteResult, true);
			deletemap.put("message", "fleamarket successfully deleted");
	    } catch (Exception e) {
	    	deletemap.put("isSuccess", false);
	    	deletemap.put("message", "Failed to delete fleamarket");
	    	deletemap.put("error", e.getMessage());
	    }
		return deletemap;
	}
}
