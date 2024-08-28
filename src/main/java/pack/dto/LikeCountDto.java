package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeCountDto {
    private int fleaMarketNo;
    private int likeCount;
    private boolean likedUser;  // 사용자 좋아요 여부 추가
    
}
