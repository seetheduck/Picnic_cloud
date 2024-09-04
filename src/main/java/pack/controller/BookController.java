package pack.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import pack.dto.BookDto;
import pack.entity.BookEntity;
import pack.service.BookService;

@RestController
public class BookController {
	@Autowired
	private BookService service;

	@GetMapping("/book")
	public Map<String, List<BookDto>> getAllBooks() {
        Map<String, List<BookDto>> categoryBooks = new HashMap<>();

        // 각 카테고리별로 책 목록을 가져와 Map에 저장
        categoryBooks.put("category1", service.getBooksByCategory(101).stream()
                                             .map(BookEntity::toDto)
                                             .collect(Collectors.toList()));
        categoryBooks.put("category2", service.getBooksByCategory(102).stream()
                                             .map(BookEntity::toDto)
                                             .collect(Collectors.toList()));
        categoryBooks.put("category3", service.getBooksByCategory(103).stream()
                                             .map(BookEntity::toDto)
                                             .collect(Collectors.toList()));

        return categoryBooks; // 카테고리별로 책 목록을 담은 Map을 반환
    }

	@GetMapping("/book/{no}")
    public BookDto getBookByNo(@PathVariable("no")int no) {
        return service.getBookById(no);
    }

}
