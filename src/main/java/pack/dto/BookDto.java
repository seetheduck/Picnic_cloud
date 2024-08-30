package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.BookEntity;
import pack.entity.CategoryEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private int no;
    private String bookName;
    private String author;
    private String publisher;
    private String publicationYear;
    private byte[] thumbnail;
    private int categoryNo;
    private String categoryName;

    public static BookEntity toEntity(BookDto bookDto) {
        if (bookDto == null) {
            return null;
        }
        
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setNo(bookDto.getCategoryNo());
        
        return BookEntity.builder()
                .no(bookDto.getNo())
                .bookName(bookDto.getBookName())
                .author(bookDto.getAuthor())
                .publisher(bookDto.getPublisher())
                .publicationYear(bookDto.getPublicationYear())
                .thumbnail(bookDto.getThumbnail())
                .category(categoryEntity)
                .build();
    }
}
