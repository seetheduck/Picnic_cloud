package pack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ch.qos.logback.core.model.Model;
import pack.dto.FleamarketDto;
import pack.model.FleamarketDao;

@Controller
public class FleamarketController {
	@Autowired
	FleamarketDao dao;
	
	@GetMapping("/")
	public List<FleamarketDto> getListAll(Model model) {
		
		return dao.findAll();
	}
}
