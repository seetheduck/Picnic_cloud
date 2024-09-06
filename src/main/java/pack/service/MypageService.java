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

    //   유저 프로필 정보 조회
    public MypageUserDto getUserProfile(Integer no) {
        // UserEntity 조회
        UserEntity userEntity = userRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 계정이 비활성화된 경우 예외 발생
        if (Boolean.TRUE.equals(userEntity.getAccountDeleteIs())) {
            throw new IllegalArgumentException("이 계정은 비활성화 되었습니다.");
        }

        // UserDetailEntity 조회
        UserDetailEntity userDetailEntity = userDetailRepository.findById(no)
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
    public void updateUserProfile(Integer no, UserDto userDto, UserDetailDto userDetailDto) {
        // UserDto와 UserDetailDto가 null이면 예외 발생
        Objects.requireNonNull(userDto, "UserDto cannot be null");
        Objects.requireNonNull(userDetailDto, "UserDetailDto cannot be null");

        // UserEntity 조회
        UserEntity user = userRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 계정이 비활성화된 경우 예외 발생
        if (Boolean.TRUE.equals(user.getAccountDeleteIs())) {
            throw new IllegalArgumentException("이 계정은 비활성화 되었습니다.");
        }

        // 이름 업데이트
        if (userDto.getName() != null && !userDto.getName().equals(user.getName())) {
            user.setName(userDto.getName());
        }

        // UserDetailEntity 조회 및 업데이트
        UserDetailEntity userDetail = userDetailRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("User details not found"));

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


    //     비밀번호 변경
    @Transactional
    public void changePassword(Integer no, String currentPassword, String newPassword) {
        // UserEntity 조회
        UserEntity user = userRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(currentPassword, user.getPw())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
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
                .collect(Collectors.toList());

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
                .collect(Collectors.toList());

        // 페이징 처리된 중고거래 게시글 목록 조회
        Page<FleamarketEntity> fleaMarketEntities = fleamarketRepository.findAllByNoIn(fleaMarketNos, pageable);
        List<FleamarketDto> fleaMarketDtos = fleaMarketEntities.stream()
                .map(FleamarketEntity::toDto)
                .collect(Collectors.toList());

        // Page 객체로 반환
        return new PageImpl<>(fleaMarketDtos, pageable, fleaMarketEntities.getTotalElements());
    }
}
