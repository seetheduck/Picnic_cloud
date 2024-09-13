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


    public FleamarketDto toDto() {
        return FleamarketDto.builder()
                .no(this.getNo())
                .title(this.getTitle())
                .price(this.getPrice())
                .contents(this.getContents())
                .createdate(this.getCreatedate())
                .updatedate(this.getUpdatedate())
                .favorite(this.getFavorite())
                .favoriteCnt(this.getFavoriteCnt() != null ? this.getFavoriteCnt() : 0)
                .blocked(this.getBlocked())
                .blockedCnt(this.getBlockedCnt())
                .userId(this.userEntity != null ? this.userEntity.getId() : null)
                .category(this.categoryEntity != null ? this.categoryEntity.getMarketNo() : null)
                .categoryName(this.categoryEntity != null ? this.categoryEntity.getCategoryName() : null)
                .build();
    }
}

