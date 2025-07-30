package com.fortunaglobal.library.repository;


import com.fortunaglobal.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Custom query to enforce ISBN consistency rules:
     * - Books with same ISBN must have identical title/author
     * - Returns true if conflicting book exists (same ISBN but different title/author)
     */
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Book b " +
            "WHERE b.isbn = :isbn " +
            "AND (b.title <> :title OR b.author <> :author)")
    boolean existsByIsbnWithDifferentTitleOrAuthor(
            @Param("isbn") String isbn,
            @Param("title") String title,
            @Param("author") String author);

    /**
     * Find books by ISBN (multiple copies allowed)
     */
    List<Book> findByIsbn(String isbn);

    /**
     * Check if a book is currently borrowed
     * (Uses BorrowRecord association - not implemented in this query)
     */
    default boolean isBookBorrowed(Long bookId) {
        // Implementation would require join with BorrowRecord
        // For simplicity, we'll assume this is handled in BorrowRecordRepository
        return false;
    }
}
