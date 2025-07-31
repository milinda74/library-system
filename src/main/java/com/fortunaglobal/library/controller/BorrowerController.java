package com.fortunaglobal.library.controller;

import com.fortunaglobal.library.model.Borrower;
import com.fortunaglobal.library.service.BorrowerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Borrowers", description = "Manage library borrowers")
@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {

    private final BorrowerService borrowerService;

    @Autowired
    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @Operation(
            summary = "Register a new borrower",
            description = "Creates a new library borrower with name and unique email",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Borrower created",
                            content = @Content(schema = @Schema(implementation = Borrower.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input"),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Email already registered")
            }
    )

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
