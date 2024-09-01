package pack.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pack.entity.FleamarketEntity;
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

		// 신고한 상태인지 확인
		ReportEntity existingReport = repository.findByUserEntity_IdAndFleamarketEntity_No(userId, fleaBoardNo);
		if (existingReport == null) {
			// 신고가 없는 상태인 경우
			ReportEntity report = ReportEntity.builder().no(repository.maxReportNum() + 1) // 새로운 신고
					.reviewNo(null)
					.fleamarketEntity(FleamarketEntity.builder().no(fleaBoardNo).build()).date(LocalDateTime.now())
					.code(code).userEntity(user) // 신고한 유저 등록
					.build();
			//신고 저장
			repository.save(report);
			return 1; //신고 성공 시 1
		}
		return 0; //신고 실패
	}

	// 신고 여부
	public boolean checkReport(String userId, int fleaBoardNo) {
	    // 신고한 상태인지 확인
	    ReportEntity existingReport = repository.findByUserEntity_IdAndFleamarketEntity_No(userId, fleaBoardNo);
	    //신고된 상태인 경우 true
	    return existingReport != null;
	}
}
