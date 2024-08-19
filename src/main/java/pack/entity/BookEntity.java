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
    private Integer bookNo;

    private String title;
    private String author;
    private String publisher;
    private Integer pyear;
    private String thumbNail;
    private String bookUrl;
    
    // Converts Entity to Dto
    public BookDto toDto() {
        return BookDto.builder()
                .bookNo(this.bookNo)
                .title(this.title)
                .author(this.author)
                .publisher(this.publisher)
                .pyear(this.pyear)
                .thumbNail(this.thumbNail)
                .bookUrl(this.bookUrl)
                .build();
    }
}
