package pack.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.ReviewDto;
import pack.entity.ReviewEntity;
import pack.exception.ForbiddenException;
import pack.repository.LikesRepository;
import pack.repository.ReviewRepository;

import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PlaceService placeService; // 장소 정보 업데이트를 위한 서비스
//    @Autowired
//    private LikesRepository likesRepository;

    // 1. 선택한 장소의 리뷰들 조회. 최신순 나열, 페이징처리
    public Page<ReviewDto> findReviewsByPlaceNo(int placeNo, Pageable pageable) {
        return reviewRepository.findByPlaceNoOrderByCreateDateDesc(placeNo, pageable)
                .map(ReviewEntity::toReviewDto);
    }

//    // 1-1. 리뷰와 좋아요 정보 포함.
//    private ReviewDto getReviewWithLikes(ReviewEntity reviewEntity) {
//        // 좋아요 수 업데이트
//        int likeCount = likesRepository.countByReviewNo(reviewEntity.getNo());
//        reviewEntity.setLikeCnt(likeCount);
//        return ReviewEntity.toReviewDto(reviewEntity);
//    }

    // 2. 리뷰 생성
    @Transactional
    public ReviewDto saveReview(ReviewDto reviewDto) {
        ReviewEntity reviewEntity = ReviewDto.toReviewEntity(reviewDto); // DTO를 엔티티로 변환
        ReviewEntity savedEntity = reviewRepository.save(reviewEntity); // 엔티티 저장

        // 6. 장소 정보 업데이트 (평점 및 리뷰 수)
        placeService.updatePlaceAfterReview(reviewDto.getPlaceNo()); // 장소의 정보 업데이트

        return ReviewEntity.toReviewDto(savedEntity); // 저장된 ReviewEntity를 DTO로 변환하여 반환
    }

    // 3. 리뷰 수정
    @Transactional
    public ReviewDto updateReview(int reviewNo, ReviewDto reviewDto, String id) {
        // 5. 작성자 확인
        if (!isUserAuthorOfReview(reviewNo, id)) {
            // 권한이 없는 경우 예외발생
            //return null;
            throw new ForbiddenException("User is not the author of the review");
        }

        reviewDto.setNo(reviewNo); // 리뷰 번호 설정
        ReviewEntity reviewEntity = ReviewDto.toReviewEntity(reviewDto);
        ReviewEntity updatedEntity = reviewRepository.save(reviewEntity);

        // 6. 장소 정보 업데이트
        placeService.updatePlaceAfterReview(reviewDto.getPlaceNo());
        return ReviewEntity.toReviewDto(updatedEntity);
    }

    // 4. 리뷰 삭제
    @Transactional
    public boolean deleteReview(int reviewNo, String id) {
        // 5. 작성자 확인
        if (!isUserAuthorOfReview(reviewNo, id)) {
            // 권한이 없는 경우 예외발생
            //return false;
            throw new ForbiddenException("User is not the author of the review");
        }
        // 6. 장소 정보 업데이트
        ReviewEntity reviewEntity = reviewRepository.findById(reviewNo)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));// 리뷰 엔티티 조회

        int placeNo = reviewEntity.getPlaceNo();// 장소 번호 추출
        reviewRepository.deleteById(reviewNo); // 리뷰 삭제
        placeService.updatePlaceAfterReview(placeNo);// 장소 정보 업데이트

        return true;
    }

    // 5. 특정 리뷰의 작성자가 현재 로그인한 사용자와 동일한지 확인
    private boolean isUserAuthorOfReview(int no, String id) {
        Optional<ReviewEntity> reviewEntity = reviewRepository.findById(no);
        return reviewEntity.isPresent() && reviewEntity.get().getId().equals(id);
    }
}
