package pack.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pack.entity.ReportEntity;
import pack.entity.UserEntity;
import pack.repository.ReportRepository;
import pack.repository.UserRepository;

@Service
public class ReportService {

	@Autowired
	ReportRepository repository;

	@Autowired
	UserRepository userRepository;

	// 신고하기
	@Transactional
	public int report(String userId, int fleaBoardNo, int code) {
		// 고객 조회
		UserEntity user = userRepository.findById(userId);
		if (user == null) {
			throw new RuntimeException("User not found");
		}

		// 신고한 상태인지 확인
		ReportEntity existingReport = repository.findByUserNoAndFleaMarketNo(user.getNo(), fleaBoardNo);
		if (existingReport == null) {
			// 신고가 없는 상태인 경우
			ReportEntity report = ReportEntity.builder()
					.no(repository.maxReportNum() + 1) // 새로운 신고 번호 생성
					.reviewNo(null)
					.fleaMarketNo(fleaBoardNo) // 플리마켓 번호 저장
					.date(LocalDateTime.now()) // 신고 날짜 저장
					.code(code) // 신고 코드 저장
					.userNo(user.getNo()) // 신고한 유저 번호 저장
					.build();
			// 신고 저장
			repository.save(report);
			return 1; // 신고 성공 시 1
		}
		return 0; // 신고 실패 (이미 신고된 경우)
	}

	// 신고 여부 확인
	public boolean checkReport(String userId, int fleaBoardNo) {
		// 고객 조회
		UserEntity user = userRepository.findById(userId);
		if (user == null) {
			throw new RuntimeException("User not found");
		}

		// 신고한 상태인지 확인
		ReportEntity existingReport = repository.findByUserNoAndFleaMarketNo(user.getNo(), fleaBoardNo);
		return existingReport != null;
	}
}
