package com.fortunaglobal.library.controller;

import com.fortunaglobal.library.model.BorrowRecord;
import com.fortunaglobal.library.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    private final BorrowService borrowService;

    @Autowired
    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

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
