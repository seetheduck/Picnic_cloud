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

import java.util.Collections;
import java.util.List;
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

    public MypageUserDto getUserProfile(Integer no) {
        UserEntity userEntity = userRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // accountDeleteIs 값이 true (1)인 경우 예외 발생
        if (Boolean.TRUE.equals(userEntity.getAccountDeleteIs())) {
            throw new IllegalArgumentException("이 계정은 비활성화 되었습니다.");
        }

        UserDetailEntity userDetailEntity = userDetailRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("User details not found"));

        return MypageUserDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .address(userDetailEntity.getAddress())
                .email(userDetailEntity.getEmail())
                .childAge(userDetailEntity.getChildAge())
                .build();
    }

    @Transactional
    public void updateUserProfile(Integer no, UserDto userDto, UserDetailDto userDetailDto) {
        // UserEntity 조회
        UserEntity user = userRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 사용자가 비활성화된 경우 예외 발생
        if (Boolean.TRUE.equals(user.getAccountDeleteIs())) {
            throw new IllegalArgumentException("이 계정은 비활성화 되었습니다.");
        }

        // UserEntity 업데이트 (ID는 수정하지 않음)
        if (userDto.getPw() != null && !passwordEncoder.matches(userDto.getPw(), user.getPw())) {
            user.setPw(passwordEncoder.encode(userDto.getPw()));
        }
        if (userDto.getName() != null && !userDto.getName().equals(user.getName())) {
            user.setName(userDto.getName());
        }

        // UserDetailEntity 업데이트
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

    public Page<PlaceDto> getLikedPlaces(String userId, Pageable pageable) {
        // QueryDSL을 사용하여 Likes 테이블과 Place 테이블을 조인하여 데이터 조회
        List<PlaceEntity> likedPlaces = myPageRepository.findLikedPlacesByUserId(userId, pageable);

        // PlaceEntity를 PlaceDto로 변환
        List<PlaceDto> likedPlaceDtos = likedPlaces.stream()
                .map(PlaceEntity::toPlaceDto)
                .collect(Collectors.toList());

        // 전체 좋아요 수 계산
        long totalLikedPlaces = myPageRepository.countLikedPlacesByUserId(userId);

        // Page 객체로 반환 (페이지, 사이즈, 총 개수 포함)
        return new PageImpl<>(likedPlaceDtos, pageable, totalLikedPlaces);
    }

    public Page<FleamarketDto> getUserPosts(String userId, Pageable pageable) {
        // 사용자 게시글 조회
        Page<FleamarketEntity> userPosts = fleamarketRepository.findUserPostsByUserId(userId, pageable);

        // FleamarketEntity를 FleamarketDto로 변환
        Page<FleamarketDto> userPostDtos = userPosts.map(FleamarketEntity::toDto);

        return userPostDtos;
    }

    public Page<FleamarketDto> getLikedFleaMarkets(String userId, Pageable pageable) {
        List<LikesEntity> likedFleaMarkets = likesRepository.findLikedFleaMarketsByUserId(userId);
        List<Integer> fleaMarketNos = likedFleaMarkets.stream()
                .map(LikesEntity::getFleaMarketNo)
                .collect(Collectors.toList());

        // 페이징 처리
        Page<FleamarketEntity> fleaMarketEntities = fleamarketRepository.findAllByNoIn(fleaMarketNos, pageable);
        List<FleamarketDto> fleaMarketDtos = fleaMarketEntities.stream()
                .map(FleamarketEntity::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(fleaMarketDtos, pageable, fleaMarketEntities.getTotalElements());
    }

}
