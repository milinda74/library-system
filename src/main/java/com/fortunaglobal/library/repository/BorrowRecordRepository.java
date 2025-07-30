package com.fortunaglobal.library.repository;

import com.fortunaglobal.library.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    // Method name-based query
    Optional<BorrowRecord> findByBookIdAndReturnDateIsNull(Long bookId);

    // Method name-based query
    Optional<BorrowRecord> findByBookIdAndBorrowerIdAndReturnDateIsNull(
            Long bookId, Long borrowerId);

    /**
     * Finds an active borrow record for a book (where return date is null)
     *
     * @param bookId ID of the book
     * @return Optional containing the active borrow record if found
     */
    @Query("SELECT br FROM BorrowRecord br " +
            "WHERE br.book.id = :bookId " +
            "AND br.returnDate IS NULL")
    Optional<BorrowRecord> findActiveRecordByBookId(@Param("bookId") Long bookId);

    /**
     * Finds an active borrow record for a specific book and borrower
     *
     * @param bookId ID of the book
     * @param borrowerId ID of the borrower
     * @return Optional containing the active borrow record if found
     */
    @Query("SELECT br FROM BorrowRecord br " +
            "WHERE br.book.id = :bookId " +
            "AND br.borrower.id = :borrowerId " +
            "AND br.returnDate IS NULL")
    Optional<BorrowRecord> findActiveRecordByBookAndBorrower(
            @Param("bookId") Long bookId,
            @Param("borrowerId") Long borrowerId);
}