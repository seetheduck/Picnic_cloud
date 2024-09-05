package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.dto.FleamarketDto;
import pack.dto.MypageUserDto;
import pack.dto.PlaceDto;
import pack.dto.SignupRequest;
import pack.service.MypageService;
import pack.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/mypage")
@CrossOrigin(origins = "*")
public class MypageController {

    @Autowired
    private MypageService mypageService;

    // 유저 정보를 가져오기
    @GetMapping(value = "/myinfo", produces = "application/json; charset=utf8")
    public ResponseEntity<?> getUserInfo(@RequestParam("no") Integer no) {
        try {
            MypageUserDto userProfile = mypageService.getUserProfile(no);
            return ResponseEntity.ok(userProfile);
        } catch (IllegalArgumentException e) {
            if ("이 계정은 비활성화 되었습니다.".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
    }

    // 유저 정보 업데이트
    @PutMapping(value = "/updateinfo", produces = "application/json; charset=utf8")
    public ResponseEntity<Void> updateUserInfo(@RequestParam("no") Integer no,
                                               @RequestBody SignupRequest signupRequest) {
        mypageService.updateUserProfile(no, signupRequest.getUserDto(), signupRequest.getUserDetailDto());
        return ResponseEntity.ok().build();
    }

    // 유저가 좋아요한 장소를 가져오기
    @GetMapping(value = "/liked-places", produces = "application/json; charset=utf8")
    public ResponseEntity<Page<PlaceDto>> getLikedPlaces(@RequestParam("userId") String userId,
                                                         Pageable pageable) {
        try {
            Page<PlaceDto> likedPlaces = mypageService.getLikedPlaces(userId, pageable);
            return ResponseEntity.ok(likedPlaces);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 유저가 작성한 중고거래 게시글 가져오기 - sort : "createdate"
    @GetMapping(value = "/posts-market", produces = "application/json; charset=utf8")
    public ResponseEntity<Page<FleamarketDto>> getUserPosts(@RequestParam("userId") String userId,
                                                            Pageable pageable) {
        try {
            Page<FleamarketDto> userPosts = mypageService.getUserPosts(userId, pageable);
            return ResponseEntity.ok(userPosts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 유저가 좋아요한 플리마켓 게시글 가져오기 - sort : "no"
    @GetMapping(value = "/liked-markets", produces = "application/json; charset=utf8")
    public ResponseEntity<Page<FleamarketDto>> getLikedFleaMarkets(@RequestParam("userId") String userId,
                                                                    Pageable pageable) {
        try {
            Page<FleamarketDto> likedFleaMarkets = mypageService.getLikedFleaMarkets(userId, pageable);
            return ResponseEntity.ok(likedFleaMarkets);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

