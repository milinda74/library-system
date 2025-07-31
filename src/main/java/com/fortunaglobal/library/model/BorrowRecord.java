package com.fortunaglobal.library.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "Book borrowing record")
@Entity
@Data
@NoArgsConstructor
public class BorrowRecord {
    @Schema(description = "Unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Borrowed book", implementation = Book.class)
    @ManyToOne
    private Book book;

    @Schema(description = "Borrower", implementation = Borrower.class)
    @ManyToOne
    private Borrower borrower;

    @Schema(description = "Date and time when book was borrowed", example = "2025-07-30T10:15:30", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime borrowDate;

    @Schema(description = "Date and time when book was returned", example = "2025-08-13T14:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime returnDate;

    public BorrowRecord(Book book, Borrower borrower, LocalDateTime borrowDate) {
        this.book = book;
        this.borrower = borrower;
        this.borrowDate = borrowDate;
    }
}
