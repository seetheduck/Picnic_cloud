package pack.entity;

import jakarta.persistence.Column;
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
    
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] thumbnail;

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
                .thumbnail(bookEntity.getThumbnail())
                .categoryNo(bookEntity.getCategory() != null ? bookEntity.getCategory().getNo() : null)
                .categoryName(bookEntity.getCategory() != null ? bookEntity.getCategory().getCategoryName() : null)  // 조인된 엔티티에서 가져오기
                .build();
    }
}
