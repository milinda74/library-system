package com.fortunaglobal.library.service;

import com.fortunaglobal.library.exception.ValidationException;
import com.fortunaglobal.library.model.Book;
import com.fortunaglobal.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    // Constructor injection
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Book registerBook(Book book) {
        // ISBN validation logic
        if(bookRepository.existsByIsbnWithDifferentTitleOrAuthor(
                book.getIsbn(), book.getTitle(), book.getAuthor())) {
            throw new ValidationException("Book with same ISBN must have identical title/author");
        }
        return bookRepository.save(book);
    }

    /**
     * Get all books in the library
     *
     * @return List of all books
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

}
