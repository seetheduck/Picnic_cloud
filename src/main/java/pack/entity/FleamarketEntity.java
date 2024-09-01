package pack.entity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.dto.FleamarketDto;

@Entity
@Table(name = "flea_market")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FleamarketEntity {
    @Id
    private int no;
    private String title;
    private Integer price; 
    private String contents;
    @Column(name="create_date")
    private LocalDateTime createdate;
    @Column(name="update_date")
    private LocalDateTime updatedate;
    private Boolean favorite;
    private Integer favoriteCnt;
    private Boolean blocked;
    private Integer blockedCnt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="category_no")
    private CategoryEntity categoryEntity;
    

    public static FleamarketDto toDto(FleamarketEntity entity) {
        return FleamarketDto.builder()
                .no(entity.getNo())
                .title(entity.getTitle())
                .price(entity.getPrice())
                .contents(entity.getContents())
                .createdate(entity.getCreatedate())
                .updatedate(entity.getUpdatedate())
                .favorite(entity.getFavorite())
                .favoriteCnt(entity.getFavoriteCnt())
                .blocked(entity.getBlocked())
                .blockedCnt(entity.getBlockedCnt())
                .userid(entity.getUserEntity().getId())
                .category(entity.getCategoryEntity() != null ? entity.getCategoryEntity().getMarketNo() : null)
                .categoryName(entity.getCategoryEntity() != null ? entity.getCategoryEntity().getCategoryName() : null)
                .build();
    }
}

