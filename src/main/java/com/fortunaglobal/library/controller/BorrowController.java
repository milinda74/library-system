package com.fortunaglobal.library.controller;

import com.fortunaglobal.library.model.BorrowRecord;
import com.fortunaglobal.library.service.BorrowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Borrowing", description = "Book borrowing operations")
@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    private final BorrowService borrowService;

    @Autowired
    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @Operation(
            summary = "Borrow a book",
            description = "Check out a book for a borrower",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Book borrowed",
                            content = @Content(schema = @Schema(implementation = BorrowRecord.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Book or borrower not found"),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Book is already borrowed")
            }
    )

    /**
     * Borrow a book
     */
    @PostMapping
    public ResponseEntity<BorrowRecord> borrowBook(
            @RequestParam Long bookId,
            @RequestParam Long borrowerId) {
        BorrowRecord record = borrowService.borrowBook(bookId, borrowerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(record);
    }

    /**
     * Return a book
     */
    @PostMapping("/return")
    public ResponseEntity<BorrowRecord> returnBook(
            @RequestParam Long bookId,
            @RequestParam Long borrowerId) {
        BorrowRecord record = borrowService.returnBook(bookId, borrowerId);
        return ResponseEntity.ok(record);
    }
}
