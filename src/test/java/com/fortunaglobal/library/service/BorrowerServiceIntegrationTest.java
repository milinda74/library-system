package com.fortunaglobal.library.service;

import com.fortunaglobal.library.exception.ConflictException;
import com.fortunaglobal.library.model.Borrower;
import com.fortunaglobal.library.repository.BorrowerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BorrowerServiceIntegrationTest {

    @Autowired
    private BorrowerService borrowerService;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Test
    void registerBorrower_Success() {
        Borrower borrower = new Borrower("John Doe", "john@example.com");
        Borrower saved = borrowerService.registerBorrower(borrower);

        assertNotNull(saved.getId());
        assertEquals("john@example.com", saved.getEmail());
    }

    @Test
    void registerBorrower_DuplicateEmail_ThrowsConflict() {
        Borrower borrower1 = new Borrower("John Doe", "john@example.com");
        borrowerService.registerBorrower(borrower1);

        Borrower borrower2 = new Borrower("Jane Doe", "john@example.com");
        assertThrows(ConflictException.class, () -> borrowerService.registerBorrower(borrower2));
    }

    @Test
    void updateBorrower_EmailConflict_ThrowsException() {

        // Create and save first borrower
        Borrower borrower1 = new Borrower("John Doe", "john@example.com");
        Borrower saved1 = borrowerService.registerBorrower(borrower1);

        // Create and save second borrower
        Borrower borrower2 = new Borrower("Jane Doe", "jane@example.com");
        Borrower saved2 = borrowerService.registerBorrower(borrower2);

        // Create update data (detached entity)
        Borrower updateData = new Borrower();
        updateData.setName("Jane Doe Updated");
        updateData.setEmail("john@example.com");  // Duplicate email

        // Verify exception
        assertThrows(ConflictException.class, () ->
                borrowerService.updateBorrower(saved2.getId(), updateData));

        // Optional: Verify no changes persisted
        Borrower refreshed = borrowerService.getBorrowerById(saved2.getId());
        assertEquals("jane@example.com", refreshed.getEmail());

    }
}