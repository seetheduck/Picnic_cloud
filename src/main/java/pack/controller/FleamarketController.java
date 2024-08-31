package pack.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import pack.entity.FleamarketEntity;
import pack.entity.UserEntity;
import pack.service.FilesService;
import pack.service.FleamarketService;
import pack.service.UserService;

@RestController
@CrossOrigin("*")
public class FleamarketController {
	@Autowired
	private FleamarketService dao;
	
	@Autowired
	private FilesService filesService ;
	
	@Autowired
	private UserService userService;
	
	//플리마켓 홈 (페이징)
	@GetMapping("/fleaMarket")
	public ResponseEntity<Page<FleamarketDto>> getListAll(
			@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "9") int size,
			@RequestParam(value ="category", required = false) String category,
			@RequestParam(value="search", required = false) String search
			) {
		
		// 페이지 요청 생성 (* import org.springframework.data.domain.PageRequest)
		// For a stable JSON structure, please use Spring Data's PagedModel ..와 같은 메시지가 뜬다.
		// 페이징된 데이터를 반환할 때, JSON 구조가 일관되지 않거나 예상치 못한 방식으로 나타남. 일관된 json 형식으로 만드는게 중요하다.
		// Spring에서는 PagedModel 또는 PagedResourcesAssembler와 같은 도구를 제공
		
		Pageable pageable = PageRequest.of(page, size); 
		
		Page<FleamarketDto> result;
		if(category == null || category.equals("전체") && (search == null || search.isEmpty())) {
			result = dao.findAll(pageable);
		} else {
		    result = dao.search(category, search, pageable);
		}
		    
	    // 결과를 ResponseEntity로 반환
	    return ResponseEntity.ok(result);
	}
	
	//상세보기
	@GetMapping("/fleaMarket/{no}")
	public FleamarketDto getOne(@PathVariable("no") Integer no
			) {
		return dao.getOne(no);
	}
	
	//생성하기
//	@PostMapping("/fleaMarket")
//	public Map<String, Object> postOne(
//	        @RequestPart("dto") String dtoJson,
//	        @RequestPart(value = "mfile", required = false) MultipartFile file) {
//	    Map<String, Object> response = new HashMap<>();
//
//	    try {
//	        ObjectMapper objectMapper = new ObjectMapper();
//	        FleamarketDto fleamarketDto = objectMapper.readValue(dtoJson, FleamarketDto.class);
//
//	        UserEntity userEntity = userService.findById(fleamarketDto.getUserid());
//	        if (userEntity == null) {
//	            response.put("isSuccess", false);
//	            response.put("message", "User not found with id: " + fleamarketDto.getUserid());
//	            return response;
//	        }
//
//	        FleamarketEntity fleaMarketEntity = FleamarketDto.toEntity(fleamarketDto);
//	        fleaMarketEntity.setUserEntity(userEntity);
//
//	        String staticDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images/";
//	        Integer newNum = dao.maxBoardNum() + 1;
//	        fleamarketDto.setNo(newNum);
//
//	        if (file != null && !file.isEmpty()) {
//	            // 공백을 언더바로 대체하고, URL 인코딩된 문자들을 제거
//	            String safeFilename = file.getOriginalFilename().replaceAll(" ", "_").replaceAll("[^a-zA-Z0-9_\\.]", "");
//	            Path imagePath = Paths.get(staticDirectory, safeFilename);
//
//	            // 이미지 저장 디렉토리 체크 및 생성
//	            File dest = imagePath.toFile();
//	            if (!dest.getParentFile().exists()) {
//	                dest.getParentFile().mkdirs();
//	            }
//
//	            // 파일을 저장하기 전에 미리 데이터베이스에 기록
//	            String path = "/images/" + safeFilename;
//	            fleamarketDto.setFilePath(path);
//	            dao.insert(fleamarketDto);
//	            file.transferTo(dest);
//
//	            // 파일 정보 저장
//	            filesService.insertFleaFile(path, newNum);
//
//	        } else {
//	            // 파일이 없는 경우
//	            dao.insert(fleamarketDto);
//	        }
//
//	        response.put("isSuccess", true);
//	        response.put("message", "FleaMarket board successfully added");
//
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	        response.put("isSuccess", false);
//	        response.put("message", "File processing error occurred: " + e.getMessage());
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        response.put("isSuccess", false);
//	        response.put("message", "Server error occurred: " + e.getMessage());
//	    }
//
//	    return response;
//	}

	//수정
	@PutMapping("/fleaMarket/{no}")
	public Map<String,Object> putOne(@PathVariable("no") Integer no,@RequestBody FleamarketDto dto) {
		Map<String,Object> updatemap = new HashMap<String, Object>();
		try {
			dto.setNo(no);
			String putResult= dao.putOne(dto);
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
			String deleteResult= dao.deleteOne(no);
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
