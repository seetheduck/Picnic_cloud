package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pack.entity.ReportEntity;

public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {

	// 신고 max번호
	@Query("select Max(r.no) from ReportEntity r")
	Integer maxReportNum();

	// 특정 유저가 특정 플리마켓에 신고하기를 눌렀는지 확인
	ReportEntity findByUserEntity_NoAndFleamarketEntity_No(Integer userNo, Integer fleaMarketNo);
	
}
