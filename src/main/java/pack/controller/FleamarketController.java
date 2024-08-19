package pack.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pack.dto.FleamarketDto;
import pack.model.FleamarketDao;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin("*")
public class FleamarketController {
	@Autowired
	FleamarketDao dao;
	
	//플리마켓 홈
	@GetMapping("/fleaMarket")
	public List<FleamarketDto> getListAll() {
		return dao.findAll();
	}
	
	//검색
	
	//카테고리
	@GetMapping("/fleaMarket?category={category}&search={search}")
	public List<FleamarketDto> getListCategory(@RequestParam("category") String category,@RequestParam("search") String input) {
		return dao.search(category,input);
	}
	
	//상세보기
	@GetMapping("/fleaMarket/{no}")
	public FleamarketDto getOne(@PathVariable("no") Integer no) {
		return dao.getOne(no);
	}
	
	
	//생성
	@PostMapping("/fleaMarket")
	public Map<String,Object> postOne(@RequestBody FleamarketDto dto) {
		//Map<>형식 사용 : json을 사용할때 응답에 대해 설정할 수 있다.(클라 -서버 상호작용 관리)
		Map<String, Object> insertmap = new HashMap<String, Object>();
		try {
			dao.insert(dto);
			insertmap.put("isSuccess", true);
			insertmap.put("message", "fleamarketBorad successfully added");
	    } catch (Exception e) {
	    	insertmap.put("isSuccess", false);
	    	insertmap.put("message", "Failed to add fleamarketBorad");
	    	insertmap.put("error", e.getMessage());
	    }
		return insertmap;
	}
	
	
	//수정
	@PutMapping("fleaMarket/{no}")
	public Map<String,Object> putOne(@PathVariable("no") Integer no,@RequestBody FleamarketDto dto) {
		Map<String,Object> updatemap = new HashMap<String, Object>();
		try {
			dto.setMNo(no);
			String putResult= dao.putOne(dto);
			updatemap.put(putResult, true);
			updatemap.put("message", "fleamarketBorad successfully updated");
	    } catch (Exception e) {
	    	updatemap.put("isSuccess", false);
	    	updatemap.put("message", "Failed to update fleamarketBorad");
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
			deletemap.put("message", "fleamarketBorad successfully updated");
	    } catch (Exception e) {
	    	deletemap.put("isSuccess", false);
	    	deletemap.put("message", "Failed to update fleamarketBorad");
	    	deletemap.put("error", e.getMessage());
	    }
		return deletemap;
	}
}
