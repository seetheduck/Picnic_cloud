package pack.entity;

import java.util.List;

import jakarta.persistence.Entity;
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
import pack.dto.CategoryDto;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEntity {

    @Id
    private Integer no;
    private Integer marketNo;
    private Integer bookNo;
    private String categoryName;

    @OneToMany(mappedBy = "categoryEntity")
    private List<FleamarketEntity> fleamarketEntity;
    
    public static CategoryDto toDto(CategoryEntity categoryEntity) {
        if (categoryEntity == null) {
            return null;
        }
        
        return CategoryDto.builder()
                .no(categoryEntity.getNo())
                .marketNo(categoryEntity.getMarketNo())
                .categoryName(categoryEntity.getCategoryName())
                .build();
    }
}