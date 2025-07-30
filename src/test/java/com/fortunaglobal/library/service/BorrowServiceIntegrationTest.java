package com.fortunaglobal.library.service;

import com.fortunaglobal.library.exception.ConflictException;
import com.fortunaglobal.library.exception.ResourceNotFoundException;
import com.fortunaglobal.library.model.Book;
import com.fortunaglobal.library.model.BorrowRecord;
import com.fortunaglobal.library.model.Borrower;
import com.fortunaglobal.library.repository.BookRepository;
import com.fortunaglobal.library.repository.BorrowRecordRepository;
import com.fortunaglobal.library.repository.BorrowerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BorrowServiceIntegrationTest {

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Test
    void borrowAndReturnBook_Success() {
        // Create test data
        Book book = new Book("1234567890", "Effective Java", "Joshua Bloch");
        Book savedBook = bookRepository.save(book);

        Borrower borrower = new Borrower("John Doe", "john@example.com");
        Borrower savedBorrower = borrowerRepository.save(borrower);

        // Borrow book
        BorrowRecord borrowRecord = borrowService.borrowBook(savedBook.getId(), savedBorrower.getId());
        assertNotNull(borrowRecord);
        assertNull(borrowRecord.getReturnDate());

        // Return book
        BorrowRecord returnRecord = borrowService.returnBook(savedBook.getId(), savedBorrower.getId());
        assertNotNull(returnRecord.getReturnDate());
    }

    @Test
    void borrowBook_AlreadyBorrowed_ThrowsConflict() {
        Book book = new Book("1234567890", "Effective Java", "Joshua Bloch");
        Book savedBook = bookRepository.save(book);

        Borrower borrower1 = new Borrower("John Doe", "john@example.com");
        Borrower borrower2 = new Borrower("Jane Doe", "jane@example.com");
        Borrower savedBorrower1 = borrowerRepository.save(borrower1);
        Borrower savedBorrower2 = borrowerRepository.save(borrower2);

        borrowService.borrowBook(savedBook.getId(), savedBorrower1.getId());
        assertThrows(ConflictException.class, () ->
                borrowService.borrowBook(savedBook.getId(), savedBorrower2.getId()));
    }

    @Test
    void returnBook_NotBorrowed_ThrowsException() {
        Book book = new Book("1234567890", "Effective Java", "Joshua Bloch");
        Book savedBook = bookRepository.save(book);

        Borrower borrower = new Borrower("John Doe", "john@example.com");
        Borrower savedBorrower = borrowerRepository.save(borrower);

        assertThrows(ResourceNotFoundException.class, () ->
                borrowService.returnBook(savedBook.getId(), savedBorrower.getId()));
    }
}