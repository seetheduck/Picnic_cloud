package pack.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pack.dto.ReviewDto;
import pack.entity.PlaceEntity;
import pack.entity.ReviewEntity;
import pack.repository.LikesReviewRepository;
import pack.repository.PlaceRepository;
import pack.repository.ReviewRepository;

@Service
public class ReviewService { //리뷰 관련 작업을 처리
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private LikesReviewRepository likesReviewRepository; // 좋아요 로직관련
	@Autowired
    private PlaceService placeService;
	@Autowired
    private LikesReviewService likesReviewService; 
	// LikesReviewService를 주입받음
	
	//리뷰 조회: 리뷰와 관련된 정보, 예를 들어 리뷰의 좋아요 수를 포함하여 반환
	//리뷰 생성, 수정, 삭제: 리뷰를 데이터베이스에 저장하거나 수정, 삭제하는 작업을 처리합니다.
	
	
	
	
	//선택한 장소의 리뷰들 조회. 최신순나열.
	public List<ReviewDto> findReviewsByPlaceNo(int placeNo){
		
		//entity to dto
		return reviewRepository.findByPlaceNoOrderByCreateDateDesc(placeNo).stream()
				.map(this::getReviewWithLikes) // 리뷰와 좋아요 정보 포함
				.collect(Collectors.toList());
	}
	
	// 리뷰와 좋아요 정보 통합
    private ReviewDto getReviewWithLikes(ReviewEntity reviewEntity) {
        // 좋아요 수 업데이트
        int likeCount = likesReviewRepository.countByReviewNo(reviewEntity.getNo());
        reviewEntity.setLikeCnt(likeCount);

        // ReviewEntity to ReviewDto 변환
        ReviewDto reviewDto = ReviewEntity.toReviewDto(reviewEntity);
        return reviewDto;
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
	// 좋아요 토글 기능 추가
	// 리뷰의 좋아요 정보 업데이트
	
}
