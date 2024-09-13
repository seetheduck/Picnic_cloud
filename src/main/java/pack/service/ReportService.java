package pack.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pack.entity.ReportEntity;
import pack.entity.UserEntity;
import pack.repository.ReportRepository;
import pack.repository.ReviewRepository;
import pack.repository.UserRepository;

@Service
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;

	// 신고하기
	@Transactional
	public int report(String userId, int fleaBoardNo, int code) {
		// 고객 조회
		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		// 신고한 상태인지 확인
		ReportEntity existingReport = reportRepository.findByUserNoAndFleaMarketNo(user.getNo(), fleaBoardNo);
		if (existingReport == null) {
			// 신고가 없는 상태인 경우
			ReportEntity report = ReportEntity.builder()
					.no(reportRepository.maxReportNum() + 1) // 새로운 신고 번호 생성
					.reviewNo(null)
					.fleaMarketNo(fleaBoardNo) // 플리마켓 번호 저장
					.date(LocalDateTime.now()) // 신고 날짜 저장
					.code(code) // 신고 코드 저장
					.userNo(user.getNo()) // 신고한 유저 번호 저장
					.build();
			// 신고 저장
			reportRepository.save(report);
			return 1; // 신고 성공 시 1
		}
		return 0; // 신고 실패 (이미 신고된 경우)
	}

	// 신고 여부 확인
	public boolean checkReport(String userId, int fleaBoardNo) {
		// 고객 조회
		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("cannot find User"));

		// 신고한 상태인지 확인
		ReportEntity existingReport = reportRepository.findByUserNoAndFleaMarketNo(user.getNo(), fleaBoardNo);
		return existingReport != null;
	}

	// 리뷰 신고 로직
	@Transactional
	public int reportReview(String userId, int reviewNo, int code) {
		// userId로 userNo 조회
		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("cannot find User"));

		int userNo = user.getNo();

		if (!reviewRepository.existsById(reviewNo)) {
			throw new RuntimeException("Review not found");
		}
		// 이미 신고한 상태인지 확인
		ReportEntity currentReport = reportRepository.findByUserNoAndReviewNo(userNo, reviewNo);
		if (currentReport == null) {
			// 신고가 없는 상태인 경우
			ReportEntity report = ReportEntity.builder()
					.no(reportRepository.maxReportNum() + 1) // 새로운 신고 번호 생성
					.reviewNo(reviewNo) // 리뷰 번호 저장
					.date(LocalDateTime.now()) // 신고 날짜 저장
					.code(code) // 신고 코드 저장
					.userNo(userNo) // 신고한 유저 번호 저장
					.build();
			// 신고 저장
			reportRepository.save(report);
			return 1; // 신고 성공 시 1
		}
		return 0; // 신고 실패 (이미 신고된 경우)
	}

	// 특정 유저가 특정 리뷰에 이미 신고했는지 확인
	public boolean hasUserReportedReview(int userNo, Integer reviewNo) {
		// 신고 상태 확인
		ReportEntity currentReport = reportRepository.findByUserNoAndReviewNo(userNo, reviewNo);
		return currentReport != null;
	}

}
