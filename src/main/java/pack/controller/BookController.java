package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pack.dto.BookDto;
import pack.entity.BookEntity;
import pack.service.BookService;

import java.util.List;

@RestController
public class BookController {

	@Autowired
	private BookService service;

    @GetMapping("/book")
    public List<BookDto> getBooksByCategory(@RequestParam("categoryNo") int categoryNo) {
        // 주어진 카테고리 번호에 따라 책 목록을 가져와 List로 변환하여 반환
        return service.getBooksByCategory(categoryNo).stream()
                .map(BookEntity::toDto)
                .toList();  // Stream.collect(Collectors.toList()) 대신 Stream.toList() 사용
    }


    @GetMapping("/book/{no}")
    public BookDto getBookByNo(@PathVariable("no")int no) {
        return service.getBookById(no);
    }

}
