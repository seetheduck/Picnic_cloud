package pack.entity;

import jakarta.persistence.*;
import lombok.*;
import pack.dto.BookDto;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookEntity {

    @Id
    private int no;
    private String bookName;
    private String author;
    private String publisher;
    private String publicationYear;
    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no", referencedColumnName = "bookNo")
    private CategoryEntity category;

    public static BookDto toDto(BookEntity bookEntity) {
        if (bookEntity == null) {
            return null;
        }

        return BookDto.builder()
                .no(bookEntity.getNo())
                .bookName(bookEntity.getBookName())
                .author(bookEntity.getAuthor())
                .publisher(bookEntity.getPublisher())
                .publicationYear(bookEntity.getPublicationYear())
                .thumbnail(bookEntity.getThumbnail())  // 그대로 String 필드로 매핑
                .categoryNo(bookEntity.getCategory() != null ? bookEntity.getCategory().getNo() : null)
                .categoryName(bookEntity.getCategory() != null ? bookEntity.getCategory().getCategoryName() : null)
                .build();
    }
}
