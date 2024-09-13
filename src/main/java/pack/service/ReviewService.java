package pack.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.LikesReviewDto;
import pack.dto.ReviewDto;
import pack.entity.ReviewEntity;
import pack.exception.ForbiddenException;
import pack.repository.LikesRepository;
import pack.repository.ReviewRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PlaceService placeService; // 장소 정보 업데이트를 위한 서비스

    @Autowired
    private JwtService jwtService; // JWT 서비스


    // 1. 선택한 장소의 리뷰들 조회. 최신순 나열, 페이징처리
    public Page<ReviewDto> findReviewsByPlaceNo(int placeNo, Pageable pageable) {
        return reviewRepository.findByPlaceNoOrderByCreateDateDesc(placeNo, pageable)
                .map(ReviewEntity::toReviewDto);
    }

    // 2. 리뷰 생성
    @Transactional
    public ReviewDto saveReview(ReviewDto reviewDto) {
        ReviewEntity reviewEntity = ReviewDto.toReviewEntity(reviewDto); // DTO를 엔티티로 변환

        // 생성 시간을 현재 시간으로 설정
        reviewEntity.setCreateDate(LocalDateTime.now());
        ReviewEntity savedEntity = reviewRepository.save(reviewEntity); // 엔티티 저장

        // 6. 장소 정보 업데이트 (평점 및 리뷰 수)
        placeService.updatePlaceAfterReview(reviewDto.getPlaceNo()); // 장소의 정보 업데이트
        return ReviewEntity.toReviewDto(savedEntity); // 저장된 ReviewEntity를 DTO로 변환하여 반환
    }

    // 3. 리뷰 수정
    @Transactional
    public ReviewDto updateReview(int reviewNo, ReviewDto reviewDto, String userId) {

        //기존 리뷰 엔티티 조회
        ReviewEntity existingReview = reviewRepository.findById(reviewNo)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        //작성자 확인 (수정자는 원 작성자여야 함)
        if (!existingReview.getId().equals(userId)) {
            throw new ForbiddenException("User is not the author of the review");
        }

        //기존 리뷰의 생성 시간 및 작성자 ID 유지
        reviewDto.setCreateDate(existingReview.getCreateDate()); // 기존 생성 시간 유지
        reviewDto.setId(existingReview.getId()); // 기존 작성자 ID 유지

        //수정할 값들 반영 (내용, 평점)
        existingReview.setContents(reviewDto.getContents()); // 내용 수정
        existingReview.setPoint(reviewDto.getPoint()); // 평점 수정

        //리뷰 저장
        ReviewEntity updatedEntity = reviewRepository.save(existingReview);

        // 장소 정보 업데이트 (리뷰 수정에 따라 장소 정보도 업데이트)
        placeService.updatePlaceAfterReview(existingReview.getPlaceNo());

        //수정된 리뷰를 DTO로 반환
        return ReviewEntity.toReviewDto(updatedEntity);
    }

    // 4. 리뷰 삭제
    @Transactional
    public boolean deleteReview(int reviewNo, String token) {
        String userId = jwtService.getUserFromToken(token); // 토큰에서 사용자 ID 추출

        // 5. 작성자 확인
        if (!isUserAuthorOfReview(reviewNo, userId)) {
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
