package com.fortunaglobal.library.service;

import com.fortunaglobal.library.exception.ValidationException;
import com.fortunaglobal.library.model.Book;
import com.fortunaglobal.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void registerBook_Success() {
        Book book = new Book("1234567890", "Effective Java", "Joshua Bloch");
        Book saved = bookService.registerBook(book);

        assertNotNull(saved.getId());
        assertEquals("1234567890", saved.getIsbn());
    }

    @Test
    void registerBook_ISBNConflict_ThrowsException() {
        Book book1 = new Book("1234567890", "Effective Java", "Joshua Bloch");
        bookService.registerBook(book1);

        Book book2 = new Book("1234567890", "Different Title", "Joshua Bloch");
        assertThrows(ValidationException.class, () -> bookService.registerBook(book2));
    }

    @Test
    void getAllBooks_ReturnsMultiple() {
        Book book1 = new Book("1234567890", "Effective Java", "Joshua Bloch");
        Book book2 = new Book("0987654321", "Clean Code", "Robert Martin");
        bookRepository.save(book1);
        bookRepository.save(book2);

        assertEquals(2, bookService.getAllBooks().size());
    }
}