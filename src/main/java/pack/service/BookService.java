package pack.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pack.dto.BookDto;
import pack.entity.BookEntity;
import pack.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;
	
	public List<BookEntity> getBooksByCategory(int categoryNo) {
        return bookRepository.findByCategoryNo(categoryNo);
    }
	
	public BookDto getBookById(int id) {
        Optional<BookEntity> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return BookEntity.toDto(book.get());
        } else {
            // 도서를 찾지 못했을 경우의 처리 (예: 예외 던지기)
            throw new RuntimeException("Book not found with id: " + id);
        }
    }
	
}
