package pack.service;

import java.time.LocalDateTime;
import java.util.Optional;

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

	// 신고하기 토글
	@Transactional
	public int toggleReport(Integer userNo, int fleaBoardNo, int code) {
		// 고객 조회
		Optional<UserEntity> optionalUser = userRepository.findById(userNo);

		// UserEntity가 존재하지 않는 경우
		if (!optionalUser.isPresent()) {
			throw new RuntimeException("존재하지 않는 사용자입니다.");
		}

		UserEntity userEntity = optionalUser.get(); // 존재하는 경우, 해당 user 받기

		// 신고한 상태인지 확인
		ReportEntity existingReport = repository.findByUserEntity_NoAndFleamarketEntity_No(userNo, fleaBoardNo);
		if (existingReport == null) {
			// 신고가 없는 상태인 경우
			ReportEntity report = ReportEntity.builder().no(repository.maxReportNum() + 1) // 새로운 신고
					.fleamarketEntity(FleamarketEntity.builder().no(fleaBoardNo).build()).date(LocalDateTime.now())
					.code(code).userEntity(userEntity) // 신고한 유저 등록
					.build();
			//신고 저장
			repository.save(report);
			return 1; //신고 성공 시 1
		}
		return 0; //신고 실패
	}

	// 신고 여부
	public boolean checkReport(Integer userNo, int fleaBoardNo) {
		// 고객 조회
		Optional<UserEntity> optionalUser = userRepository.findById(userNo);

		// UserEntity가 존재하지 않는 경우
		if (!optionalUser.isPresent()) {
		}

		// 신고한 상태인지 확인
		ReportEntity existingReport = repository.findByUserEntity_NoAndFleamarketEntity_No(userNo, fleaBoardNo);
		
		//신고된 상태인 경우 true
		return existingReport != null;
	}
}
