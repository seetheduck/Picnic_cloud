package pack.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pack.dto.ReviewDto;
import pack.entity.PlaceEntity;
import pack.entity.ReviewEntity;
import pack.repository.PlaceRepository;
import pack.repository.ReviewRepository;

@Service
public class ReviewService {
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
    private PlaceService placeService;
	
	//선택한 장소의 리뷰들 조회. 최신순나열.
	public List<ReviewDto> findReviewsByPlaceNo(int placeNo){
		
		//entity to dto
		return reviewRepository.findByPlaceNoOrderByCreateDateDesc(placeNo).stream()
				.map(ReviewEntity::toReviewDto)
				.collect(Collectors.toList());
	}

	// 리뷰가 추가될 때 장소의 평점과 리뷰 수를 업데이트
    public ReviewDto saveReview(ReviewDto reviewDto) {
        ReviewEntity reviewEntity = ReviewDto.toReviewEntity(reviewDto); // DTO를 엔티티로 변환
        ReviewEntity savedEntity = reviewRepository.save(reviewEntity); // 엔티티 저장

        placeService.updatePlaceAfterReview(reviewDto.getPlaceNo()); // 장소의 정보 업데이트

        return ReviewEntity.toReviewDto(savedEntity); // 저장된 ReviewEntity를 DTO로 변환하여 반환
    }
	
	
	//로그인하였을때, 작성자가 작성한 글만 수정삭제버튼이 뜨게 할것. 
	//이때에도 해당 리뷰가 생성인지 수정인지 확인하는 로직이 필요할까? 이때 로그인한 유저정보와 ==인지를 확인해야하는것 아닐까?
    //이부분은 로그인 기능이 완성되면 추가로 코드작업하기.

	// 리뷰 삭제
	public void deleteReview(int no) {
		//정말 삭제하시겠습니까라는 알람창 뜨도록
		reviewRepository.deleteById(no);
	}
}
