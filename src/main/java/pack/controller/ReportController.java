package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pack.dto.ReportRequestDto;
import pack.service.ReportService;

@RestController
@CrossOrigin("*")
public class ReportController {

	@Autowired
	ReportService reportService;

	@PostMapping("/fleaMarket/report")
	public String report(@RequestBody ReportRequestDto reportRequest) {

	        try {
	            Integer reportCodeInteger = Integer.parseInt(reportRequest.getReportCode());
	            Integer fleaBoardNoInteger = Integer.parseInt(reportRequest.getFleaBoardNo());

	            int result = reportService.report(reportRequest.getUserId(), fleaBoardNoInteger, reportCodeInteger);
	            return result == 1 ? "신고 성공" : "이미 신고됨";
	        } catch (NumberFormatException e) {
	            return "잘못된 입력입니다.";
	        }
	}
}
