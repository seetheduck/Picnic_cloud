package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pack.entity.ReportEntity;

public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {

	// 신고 max번호
	@Query("SELECT COALESCE(MAX(r.no), 0) FROM ReportEntity r")
	int maxReportNum();
	//테이블이 비어 있을 때, 즉 r.no 값이 없는 경우, 이 쿼리(select Max(r.no) from ReportEntity r)는 null을 반환

	// flea_market_no를 기준으로 report 테이블에서 데이터 삭제
	void deleteByFleaMarketNo(int fleaMarketNo);

	// 특정 유저가 특정 플리마켓에 신고하기를 눌렀는지 확인
	ReportEntity findByUserNoAndFleaMarketNo(int userNo, Integer fleaMarketNo);

	// 특정 유저가 특정 리뷰에 신고하기를 눌렀는지 확인
	ReportEntity findByUserNoAndReviewNo(int userNo, Integer reviewNo);

}
