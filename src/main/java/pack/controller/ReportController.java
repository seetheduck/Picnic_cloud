package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pack.dto.ReportDto;
import pack.dto.ReportRequestDto;
import pack.dto.ReportReviewDto;
import pack.service.ReportService;

@RestController
@CrossOrigin("*")
public class ReportController {

	@Autowired
	ReportService reportService;

	@PostMapping("/fleaMarket/report")
	public String report(@RequestBody ReportRequestDto reportRequest) {
		try {
			System.out.println(reportRequest);
			Integer reportCodeInteger = Integer.parseInt(reportRequest.getReportCode());
			Integer fleaBoardNoInteger = Integer.parseInt(reportRequest.getFleaBoardNo());

			int result = reportService.report(reportRequest.getUserId(), fleaBoardNoInteger, reportCodeInteger);
			return result == 1 ? "신고 성공" : "이미 신고됨";
		} catch (NumberFormatException e) {
			return "잘못된 입력입니다.";
		}
	}

	@PostMapping("/review/report")
	public ResponseEntity<String> reportReview(@RequestBody ReportReviewDto reportReviewDto) {
		try {
			String userId = reportReviewDto.getUserId();
			Integer reviewNo = reportReviewDto.getReviewNo();
			int code = reportReviewDto.getCode();

			// 리뷰 신고 처리
			int result = reportService.reportReview(userId, reviewNo, code);
			if (result == 1) {
				return ResponseEntity.ok("신고 성공");
			} else {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 신고됨");
			}
		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().body("잘못된 입력입니다.");
		}
	}

}


