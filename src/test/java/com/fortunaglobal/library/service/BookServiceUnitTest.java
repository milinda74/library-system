package com.fortunaglobal.library.service;

import com.fortunaglobal.library.exception.ValidationException;
import com.fortunaglobal.library.model.Book;
import com.fortunaglobal.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceUnitTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void registerBook_Success() {
        Book book = new Book("1234567890", "Effective Java", "Joshua Bloch");
        when(bookRepository.existsByIsbnWithDifferentTitleOrAuthor("1234567890", "Effective Java", "Joshua Bloch"))
                .thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.registerBook(book);

        assertNotNull(result);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void registerBook_InvalidISBN_ThrowsException() {
        Book book = new Book("1234567890", "Effective Java", "Joshua Bloch");
        when(bookRepository.existsByIsbnWithDifferentTitleOrAuthor("1234567890", "Effective Java", "Joshua Bloch"))
                .thenReturn(true);

        assertThrows(ValidationException.class, () -> bookService.registerBook(book));
    }

    @Test
    void getAllBooks_Success() {
        bookService.getAllBooks();
        verify(bookRepository, times(1)).findAll();
    }
}
