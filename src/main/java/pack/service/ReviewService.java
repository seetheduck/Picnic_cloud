package pack.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pack.dto.ReviewDto;
import pack.entity.ReviewEntity;
import pack.repository.ReviewRepository;

@Service
public class ReviewService {
	@Autowired
	private ReviewRepository reviewRepository;
	
	//선택한 장소의 리뷰들 조회
	public List<ReviewDto> findReviewsByPlaceNo(int placeNo){
		
		//entity to dto
		return reviewRepository.findByPlaceNo(placeNo).stream()
				.map(ReviewEntity::toReviewDto)
				.collect(Collectors.toList());
	}
	// 리뷰 생성 및 수정
	public ReviewDto saveReview(ReviewDto reviewDto) {
        ReviewEntity reviewEntity = ReviewDto.toReviewEntity(reviewDto); // toentity 변환
        ReviewEntity savedEntity = reviewRepository.save(reviewEntity); // 변환된 entity 저장
        return ReviewEntity.toReviewDto(savedEntity);//저장된 ReviewEntity를 ReviewDto로 변환하여 반환
    }
	//로그인하였을때, 작성자가 작성한 글만 수정삭제버튼이 뜨게 할것. 
	//이때에도 해당 리뷰가 생성인지 수정인지 확인하는 로직이 필요할까? 이때 로그인한 유저정보와 ==인지를 확인해야하는것 아닐까?

	// 리뷰 삭제
	public void deleteReview(int no) {
		//정말 삭제하시겠습니까라는 알람창 뜨도록
		reviewRepository.deleteById(no);
	}
}
