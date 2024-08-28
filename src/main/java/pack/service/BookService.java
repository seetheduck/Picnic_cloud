package pack.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pack.entity.BookEntity;
import pack.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;
	
	public List<BookEntity> getBooksByCategory(int categoryNo) {
        return bookRepository.findByCategoryNo(categoryNo);
    }
	
}
