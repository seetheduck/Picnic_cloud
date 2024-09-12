package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.*;
import pack.entity.*;
import pack.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MypageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private MyPageRepository myPageRepository;

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    @Autowired
    private FleamarketRepository fleamarketRepository;

    @Autowired
    private LikesRepository likesRepository;

    public static final String USER_NOT_FOUND_MESSAGE = "User not found";

    //   유저 프로필 정보 조회
    public MypageUserDto getUserProfile(String userId) {
        // userId로 UserEntity 조회
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND_MESSAGE));

        // 계정이 비활성화된 경우 예외 발생
        if (Boolean.TRUE.equals(userEntity.getAccountDeleteIs())) {
            throw new IllegalArgumentException("이 계정은 비활성화 되었습니다.");
        }

        // userId로 UserDetailEntity 조회
        UserDetailEntity userDetailEntity = userDetailRepository.findById(userEntity.getNo())
                .orElseThrow(() -> new IllegalArgumentException("User details not found"));

        // 프로필 정보를 MypageUserDto로 변환
        return MypageUserDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .address(userDetailEntity.getAddress())
                .email(userDetailEntity.getEmail())
                .childAge(userDetailEntity.getChildAge())
                .build();
    }

    //   유저 프로필 업데이트
    @Transactional
    public void updateUserProfile(String userId, UserDto userDto, UserDetailDto userDetailDto) {
        // UserEntity 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND_MESSAGE));

        // 계정이 비활성화된 경우 예외 발생
        if (Boolean.TRUE.equals(user.getAccountDeleteIs())) {
            throw new IllegalArgumentException("이 계정은 비활성화 되었습니다.");
        }

        // UserDetailEntity 조회 및 업데이트
        UserDetailEntity userDetail = userDetailRepository.findById(user.getNo())
                .orElseThrow(() -> new IllegalArgumentException("User details not found"));

        // 필요한 필드 업데이트
        if (userDto.getName() != null && !userDto.getName().equals(user.getName())) {
            user.setName(userDto.getName());
        }
        if (userDetailDto.getEmail() != null && !userDetailDto.getEmail().equals(userDetail.getEmail())) {
            userDetail.setEmail(userDetailDto.getEmail());
        }
        if (userDetailDto.getAddress() != null && !userDetailDto.getAddress().equals(userDetail.getAddress())) {
            userDetail.setAddress(userDetailDto.getAddress());
        }
        if (userDetailDto.getChildAge() >= 0 && userDetailDto.getChildAge() != userDetail.getChildAge()) {
            userDetail.setChildAge(userDetailDto.getChildAge());
        }

        // 변경된 엔티티 저장
        userRepository.save(user);
        userDetailRepository.save(userDetail);
    }


    // 비밀번호 변경
    @Transactional
    public void changePassword(String userId, String currentPassword, String newPassword) {
        // userId로 UserEntity 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND_MESSAGE));

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(currentPassword, user.getPw())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 새 비밀번호가 현재 비밀번호와 동일한지 확인
        if (passwordEncoder.matches(newPassword, user.getPw())) {
            throw new IllegalArgumentException("새 비밀번호는 현재 비밀번호와 다르게 설정해야 합니다.");
        }

        // 새 비밀번호 해시화 후 저장
        user.setPw(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


    //  유저가 좋아요한 장소 목록 조회
    public Page<PlaceDto> getLikedPlaces(String userId, Pageable pageable) {
        // 좋아요한 장소 조회
        List<PlaceEntity> likedPlaces = myPageRepository.findLikedPlacesByUserId(userId, pageable);

        // PlaceEntity를 PlaceDto로 변환
        List<PlaceDto> likedPlaceDtos = likedPlaces.stream()
                .map(PlaceEntity::toPlaceDto)
                .toList();  // Collectors.toList() 대신 Stream.toList() 사용

        // 좋아요한 장소 수 조회
        long totalLikedPlaces = myPageRepository.countLikedPlacesByUserId(userId);

        // Page 객체로 반환
        return new PageImpl<>(likedPlaceDtos, pageable, totalLikedPlaces);
    }


    //  유저가 작성한 중고거래 게시글 조회
    public Page<FleamarketDto> getUserPosts(String userId, Pageable pageable) {
        // 유저가 작성한 게시글 조회
        Page<FleamarketEntity> userPosts = fleamarketRepository.findUserPostsByUserId(userId, pageable);

        // FleamarketEntity를 FleamarketDto로 변환
        Page<FleamarketDto> userPostDtos = userPosts.map(FleamarketEntity::toDto);

        return userPostDtos;
    }

    //  유저가 좋아요한 중고거래 게시글 조회
    public Page<FleamarketDto> getLikedFleaMarkets(String userId, Pageable pageable) {
        // 좋아요한 중고거래 게시글 목록 조회
        List<LikesEntity> likedFleaMarkets = likesRepository.findLikedFleaMarketsByUserId(userId);
        List<Integer> fleaMarketNos = likedFleaMarkets.stream()
                .map(LikesEntity::getFleaMarketNo)
                .toList();

        // 페이징 처리된 중고거래 게시글 목록 조회
        Page<FleamarketEntity> fleaMarketEntities = fleamarketRepository.findAllByNoIn(fleaMarketNos, pageable);
        List<FleamarketDto> fleaMarketDtos = fleaMarketEntities.stream()
                .map(FleamarketEntity::toDto)
                .toList();

        // Page 객체로 반환
        return new PageImpl<>(fleaMarketDtos, pageable, fleaMarketEntities.getTotalElements());
    }

    @Transactional
    public void deactivateAccount(Integer userId) {
        // 사용자 ID로 UserEntity를 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // UserDetailEntity를 조회
        UserDetailEntity userDetail = userDetailRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User details not found"));

        // 계정 비활성화
        user.setAccountDeleteIs(true);
        userDetail.setAccountDeleteDate(LocalDateTime.now());  // 비활성화 날짜 설정

        // 변경된 엔티티를 저장
        userRepository.save(user);
        userDetailRepository.save(userDetail);
    }


}
