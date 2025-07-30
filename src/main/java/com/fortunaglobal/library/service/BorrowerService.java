package com.fortunaglobal.library.service;

import com.fortunaglobal.library.exception.ConflictException;
import com.fortunaglobal.library.exception.ResourceNotFoundException;
import com.fortunaglobal.library.exception.ValidationException;
import com.fortunaglobal.library.model.Borrower;
import com.fortunaglobal.library.repository.BorrowerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;

    public BorrowerService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    /**
     * Register a new borrower
     *
     * @param borrower Borrower to register
     * @return Registered borrower
     * @throws ConflictException if email already exists
     */
    @Transactional
    public Borrower registerBorrower(Borrower borrower) {
        // Validate input
        if (borrower.getName() == null || borrower.getName().trim().isEmpty()) {
            throw new ValidationException("Name is required");
        }

        if (borrower.getEmail() == null || borrower.getEmail().trim().isEmpty()) {
            throw new ValidationException("Email is required");
        }

        // Check for duplicate email
        if (borrowerRepository.findByEmail(borrower.getEmail()).isPresent()) {
            throw new ConflictException("Email '%s' is already registered", borrower.getEmail());
        }

        return borrowerRepository.save(borrower);
    }

    /**
     * Get borrower by ID
     *
     * @param id Borrower ID
     * @return Found borrower
     * @throws ResourceNotFoundException if borrower not found
     */
    public Borrower getBorrowerById(Long id) {
        return borrowerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found with id: " + id));
    }

    /**
     * Get all borrowers
     *
     * @return List of all borrowers
     */
    public List<Borrower> getAllBorrowers() {
        return borrowerRepository.findAll();
    }

    /**
     * Update an existing borrower
     *
     * @param id Borrower ID to update
     * @param borrowerDetails Updated borrower details
     * @return Updated borrower
     * @throws ResourceNotFoundException if borrower not found
     * @throws ConflictException if email conflict occurs
     */
    @Transactional
    public Borrower updateBorrower(Long id, Borrower borrowerDetails) {
        Borrower borrower = borrowerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found with id: " + id));

        // Validate input
        if (borrowerDetails.getName() == null || borrowerDetails.getName().trim().isEmpty()) {
            throw new ValidationException("Name is required");
        }

        if (borrowerDetails.getEmail() == null || borrowerDetails.getEmail().trim().isEmpty()) {
            throw new ValidationException("Email is required");
        }

        // Check for email conflict (excluding current borrower)
        String newEmail = borrowerDetails.getEmail();
        if (!borrower.getEmail().equals(newEmail)) {
            if (borrowerRepository.existsByEmailExcludingId(newEmail, id)) {
                throw new ConflictException("Email '%s' is already registered", newEmail);
            }
        }

        // Update borrower details
        borrower.setName(borrowerDetails.getName());
        borrower.setEmail(borrowerDetails.getEmail());

        return borrowerRepository.save(borrower);
    }

    /**
     * Delete a borrower by ID
     *
     * @param id Borrower ID to delete
     * @throws ResourceNotFoundException if borrower not found
     * @throws ConflictException if borrower has active borrowings
     */
    @Transactional
    public void deleteBorrower(Long id) {
        Borrower borrower = borrowerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found with id: " + id));

        // Check for active borrowings (implementation depends on BorrowRecordRepository)
        // For simplicity, we'll assume this check exists
        if (hasActiveBorrowings(borrower)) {
            throw new ConflictException("Cannot delete borrower with active borrowings");
        }

        borrowerRepository.delete(borrower);
    }

    /**
     * Check if borrower has active borrowings
     * (This method would typically be implemented with BorrowRecordRepository)
     */
    private boolean hasActiveBorrowings(Borrower borrower) {
        // Implementation would require BorrowRecordRepository
        // For now, return false as placeholder
        return false;
    }
}
