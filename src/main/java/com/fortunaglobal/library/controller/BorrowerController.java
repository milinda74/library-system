package com.fortunaglobal.library.controller;

import com.fortunaglobal.library.model.Borrower;
import com.fortunaglobal.library.service.BorrowerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {

    private final BorrowerService borrowerService;

    @Autowired
    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    /**
     * Register a new borrower
     */
    @PostMapping
    public ResponseEntity<Borrower> registerBorrower(@Valid @RequestBody Borrower borrower) {
        Borrower newBorrower = borrowerService.registerBorrower(borrower);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBorrower);
    }

    /**
     * Get borrower by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Borrower> getBorrowerById(@PathVariable Long id) {
        Borrower borrower = borrowerService.getBorrowerById(id);
        return ResponseEntity.ok(borrower);
    }

    /**
     * Get all borrowers
     */
    @GetMapping
    public ResponseEntity<List<Borrower>> getAllBorrowers() {
        List<Borrower> borrowers = borrowerService.getAllBorrowers();
        return ResponseEntity.ok(borrowers);
    }

    /**
     * Update a borrower
     */
    @PutMapping("/{id}")
    public ResponseEntity<Borrower> updateBorrower(
            @PathVariable Long id,
            @Valid @RequestBody Borrower borrowerDetails) {
        Borrower updatedBorrower = borrowerService.updateBorrower(id, borrowerDetails);
        return ResponseEntity.ok(updatedBorrower);
    }

    /**
     * Delete a borrower
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrower(@PathVariable Long id) {
        borrowerService.deleteBorrower(id);
        return ResponseEntity.noContent().build();
    }
}
