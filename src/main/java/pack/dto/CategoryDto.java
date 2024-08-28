package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.CategoryEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private int no;
    private int marketNo;
    private int bookNo;
    private String categoryName;

    public static CategoryEntity toEntity(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }
        
        return CategoryEntity.builder()
                .no(categoryDto.getNo())
                .marketNo(categoryDto.getMarketNo())
                .bookNo(categoryDto.getBookNo())
                .categoryName(categoryDto.getCategoryName())
                .build();
    }
}