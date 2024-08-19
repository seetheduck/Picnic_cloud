package pack.dto;

import lombok.*;
import pack.entity.BookEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {

    private Integer bookNo;
    private String title;
    private String author;
    private String publisher;
    private Integer pyear;
    private String thumbNail;
    private String bookUrl;

    // Converts Dto to Entity
    public BookEntity toEntity() {
        return BookEntity.builder()
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

