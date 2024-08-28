package pack.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ReportController {

	//신고하기
	@GetMapping("/fleaMarket/reprot")
	public String getMethodName(@RequestParam String param) {
		return new String();
	}
	
}
