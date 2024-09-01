package pack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.dto.LikesDto;

@Entity
@Table(name = "likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesEntity {
	@Id
	private int no;
	private String userId;
	private Integer reviewNo;
	private Integer fleaMarketNo;
	
	//toDto
	public static LikesDto toDto(LikesEntity entity) {
		return LikesDto.builder()
				.no(entity.getNo())
				.userId(entity.getUserId())
				.fleaMarketNo(entity.getFleaMarketNo())
				.build();
	}
}
