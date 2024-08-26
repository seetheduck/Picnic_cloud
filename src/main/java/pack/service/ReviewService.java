package pack.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
//	// 리뷰 생성 및 수정
//	public ReviewDto saveReview(ReviewDto reviewDto) {
//        ReviewEntity reviewEntity = ReviewDto.toReviewEntity(reviewDto);
//        ReviewEntity savedEntity = reviewRepository.save(reviewEntity);
//        return ReviewEntity.toReviewDto(savedEntity);
//    }

	// 리뷰 삭제
   
}
