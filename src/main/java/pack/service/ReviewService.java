package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.LikesReviewDto;
import pack.dto.ReviewDto;
import pack.entity.LikesEntity;
import pack.entity.ReviewEntity;
import pack.repository.LikesRepository;
import pack.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private LikesRepository likesRepository; // 좋아요 로직관련

    @Autowired
    private PlaceService placeService; // 장소 정보 업데이트를 위한 서비스

    @Autowired
    private LikesService likesService;

    //1. 선택한 장소의 리뷰들 조회. 최신순나열.
    public Page<ReviewDto> findReviewsByPlaceNo(int placeNo, Pageable pageable) {
        return reviewRepository.findByPlaceNoOrderByCreateDateDesc(placeNo, pageable).map(this::getReviewWithLikes);
    }

    // 1-1. 리뷰와 좋아요 정보 포함.
    private ReviewDto getReviewWithLikes(ReviewEntity reviewEntity) {
        // 좋아요 수 업데이트
        int likeCount = likesRepository.countByReviewNo(reviewEntity.getNo());
        reviewEntity.setLikeCnt(likeCount);
        return ReviewEntity.toReviewDto(reviewEntity);
    }


    // 2. 리뷰 생성
    // 리뷰의 생성 시 리뷰 수 카운트와 평점 로직은 장소 서비스에서 처리.
    public ReviewDto saveReview(ReviewDto reviewDto) {
        ReviewEntity reviewEntity = ReviewDto.toReviewEntity(reviewDto); // DTO를 엔티티로 변환
        ReviewEntity savedEntity = reviewRepository.save(reviewEntity); // 엔티티 저장

        // 장소 정보를 업데이트 (평점 및 리뷰 수)
        placeService.updatePlaceAfterReview(reviewDto.getPlaceNo()); // 장소의 정보 업데이트

        return ReviewEntity.toReviewDto(savedEntity); // 저장된 ReviewEntity를 DTO로 변환하여 반환
    }

    //3. 리뷰 수정
    public ReviewDto updateReview(int reviewNo, ReviewDto reviewDto, String userId) {
        // 작성자 확인
        if (!isUserAuthorOfReview(reviewNo, userId)) {
            // 권한이 없는 경우 null 반환
            return null;
        }

        reviewDto.setNo(reviewNo);
        ReviewEntity reviewEntity = ReviewDto.toReviewEntity(reviewDto);
        ReviewEntity updatedEntity = reviewRepository.save(reviewEntity);

        // 장소 정보 업데이트
        placeService.updatePlaceAfterReview(reviewDto.getPlaceNo());

        return ReviewEntity.toReviewDto(updatedEntity);
    }


    // 4. 리뷰 삭제
    public boolean deleteReview(int reviewNo, String userId) {
        // 작성자 확인
        if (!isUserAuthorOfReview(reviewNo, userId)) {
            // 권한이 없는 경우 false 반환
            return false;
        }

        reviewRepository.deleteById(reviewNo);
        return true;
    }

    // 특정 리뷰의 작성자가 현재 로그인한 사용자와 동일한지 확인
    private boolean isUserAuthorOfReview(int no, String userId) {
        Optional<ReviewEntity> reviewEntity = reviewRepository.findById(no);
        return reviewEntity.isPresent() && reviewEntity.get().getId().equals(userId);
    }

}
