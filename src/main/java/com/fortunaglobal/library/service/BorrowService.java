package com.fortunaglobal.library.service;

import com.fortunaglobal.library.exception.ConflictException;
import com.fortunaglobal.library.exception.ResourceNotFoundException;
import com.fortunaglobal.library.model.Book;
import com.fortunaglobal.library.model.BorrowRecord;
import com.fortunaglobal.library.model.Borrower;
import com.fortunaglobal.library.repository.BookRepository;
import com.fortunaglobal.library.repository.BorrowRecordRepository;
import com.fortunaglobal.library.repository.BorrowerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BorrowService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;

    public BorrowService(
            BorrowRecordRepository borrowRecordRepository,
            BookRepository bookRepository,
            BorrowerRepository borrowerRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.bookRepository = bookRepository;
        this.borrowerRepository = borrowerRepository;
    }

    /**
     * Borrow a book for a borrower
     *
     * @param bookId ID of the book to borrow
     * @param borrowerId ID of the borrower
     * @return Created borrow record
     * @throws ResourceNotFoundException if book or borrower not found
     * @throws ConflictException if book is already borrowed
     */
    @Transactional
    public BorrowRecord borrowBook(Long bookId, Long borrowerId) {
        // 1. Validate book exists
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        // 2. Validate borrower exists
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found with id: " + borrowerId));

        // 3. Check if book is currently borrowed
        Optional<BorrowRecord> activeRecord = borrowRecordRepository
                .findByBookIdAndReturnDateIsNull(bookId);

        if (activeRecord.isPresent()) {
            throw new ConflictException("Book with ID " + bookId + " is already borrowed");
        }

        // 4. Create new borrow record
        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setBorrower(borrower);
        record.setBorrowDate(LocalDateTime.now());

        return borrowRecordRepository.save(record);
    }

    /**
     * Return a borrowed book
     *
     * @param bookId ID of the book being returned
     * @param borrowerId ID of the borrower returning the book
     * @return Updated borrow record
     * @throws ResourceNotFoundException if no active borrow record found
     */
    @Transactional
    public BorrowRecord returnBook(Long bookId, Long borrowerId) {
        // 1. Find active borrow record
        BorrowRecord record = borrowRecordRepository
                .findByBookIdAndBorrowerIdAndReturnDateIsNull(bookId, borrowerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No active borrow record found for book ID: " + bookId +
                                " and borrower ID: " + borrowerId));

        // 2. Update return date
        record.setReturnDate(LocalDateTime.now());

        return borrowRecordRepository.save(record);
    }
}
