package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
	
    private String title;         // 책 제목
    private String[] authors;     // 저자들
    private String publisher;     // 출판사
    private String isbn;          // ISBN 코드
    private String thumbnailUrl;  // 썸네일 이미지 URL
    private String datetime;      // 출판일 또는 API 응답의 날짜 정보
    
}
