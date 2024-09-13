package pack.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.CategoryEntity;
import pack.entity.FleamarketEntity;
import pack.entity.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FleamarketDto {

	private int no;
	private String userId;
	private String title;
	private int price;
	private String contents;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime createdate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime updatedate;
	private Boolean favorite = false;
	private int favoriteCnt = 0;
	private Integer category;
	private Boolean blocked;
	private Integer blockedCnt;

	private List<String> files;

	private String categoryName;

	// 페이징 처리
	private int totalPages, currentPage;
	private Long totalElements;

	// 채팅번호
//    private List<Integer> cNo; 

	// dto를 entity로 변환
	public static FleamarketEntity toEntity(FleamarketDto dto) {
		if (dto == null) {
			return null;
		}

		return  FleamarketEntity.builder()
				.no(dto.getNo())
				.title(dto.getTitle())
				.price(dto.getPrice())
				.contents(dto.getContents())
				.createdate(LocalDateTime.now())
				.updatedate(dto.getUpdatedate())
				.favorite(dto.getFavorite())
				.favoriteCnt(dto.getFavoriteCnt())
				.blocked(dto.getBlocked())
				.blockedCnt(dto.getBlockedCnt())
				.userEntity(UserEntity.builder().id(dto.getUserId()).build())
				.categoryEntity(CategoryEntity.builder().no(dto.getCategory()).build())
				.build();
	}
}
