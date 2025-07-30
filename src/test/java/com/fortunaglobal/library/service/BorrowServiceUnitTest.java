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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowServiceUnitTest {

    @Mock
    private BorrowRecordRepository borrowRecordRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowerRepository borrowerRepository;

    @InjectMocks
    private BorrowService borrowService;

    @Test
    void borrowBook_Success() {
        Book book = new Book(1L, "1234567890", "Effective Java", "Joshua Bloch");
        Borrower borrower = new Borrower(1L, "John Doe", "john@example.com");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(borrowRecordRepository.findByBookIdAndReturnDateIsNull(1L)).thenReturn(Optional.empty());
        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenAnswer(inv -> inv.getArgument(0));

        BorrowRecord record = borrowService.borrowBook(1L, 1L);

        assertNotNull(record);
        assertEquals(1L, record.getBook().getId());
        assertEquals(1L, record.getBorrower().getId());
        assertNotNull(record.getBorrowDate());
        assertNull(record.getReturnDate());
    }

    @Test
    void borrowBook_BookNotFound_ThrowsException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> borrowService.borrowBook(1L, 1L));
    }

    @Test
    void borrowBook_AlreadyBorrowed_ThrowsConflict() {
        Book book = new Book(1L, "1234567890", "Effective Java", "Joshua Bloch");
        Borrower borrower = new Borrower(1L, "John Doe", "john@example.com");
        BorrowRecord existing = new BorrowRecord();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(borrowRecordRepository.findByBookIdAndReturnDateIsNull(1L)).thenReturn(Optional.of(existing));

        assertThrows(ConflictException.class, () -> borrowService.borrowBook(1L, 1L));
    }

    @Test
    void returnBook_Success() {
        Book book = new Book(1L, "1234567890", "Effective Java", "Joshua Bloch");
        Borrower borrower = new Borrower(1L, "John Doe", "john@example.com");
        BorrowRecord record = new BorrowRecord(book, borrower, LocalDateTime.now().minusDays(5));

        when(borrowRecordRepository.findByBookIdAndBorrowerIdAndReturnDateIsNull(1L, 1L))
                .thenReturn(Optional.of(record));
        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenAnswer(inv -> inv.getArgument(0));

        BorrowRecord result = borrowService.returnBook(1L, 1L);

        assertNotNull(result.getReturnDate());
    }
}
