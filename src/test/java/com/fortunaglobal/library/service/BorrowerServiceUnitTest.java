package com.fortunaglobal.library.service;

import com.fortunaglobal.library.exception.ConflictException;
import com.fortunaglobal.library.exception.ResourceNotFoundException;
import com.fortunaglobal.library.exception.ValidationException;
import com.fortunaglobal.library.model.Borrower;
import com.fortunaglobal.library.repository.BorrowerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowerServiceUnitTest {

    @Mock
    private BorrowerRepository borrowerRepository;

    @InjectMocks
    private BorrowerService borrowerService;

    @Test
    void registerBorrower_Success() {
        Borrower borrower = new Borrower("John Doe", "john@example.com");
        when(borrowerRepository.findByEmail("john@example.com")).thenReturn(Optional.empty());
        when(borrowerRepository.save(any(Borrower.class))).thenReturn(borrower);

        Borrower result = borrowerService.registerBorrower(borrower);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(borrowerRepository, times(1)).save(borrower);
    }

    @Test
    void registerBorrower_DuplicateEmail_ThrowsConflict() {
        Borrower borrower = new Borrower("John Doe", "john@example.com");
        when(borrowerRepository.findByEmail("john@example.com")).thenReturn(Optional.of(borrower));

        assertThrows(ConflictException.class, () -> borrowerService.registerBorrower(borrower));
    }

    @Test
    void getBorrowerById_Found() {
        Borrower borrower = new Borrower("John Doe", "john@example.com");
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));

        Borrower result = borrowerService.getBorrowerById(1L);

        assertEquals("John Doe", result.getName());
    }

    @Test
    void getBorrowerById_NotFound_ThrowsException() {
        when(borrowerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> borrowerService.getBorrowerById(1L));
    }

    @Test
    void updateBorrower_Success() {
        Borrower existing = new Borrower(1L, "John Doe", "john@example.com");
        Borrower update = new Borrower(1L, "John Updated", "john.new@example.com");

        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(borrowerRepository.existsByEmailExcludingId("john.new@example.com", 1L)).thenReturn(false);
        when(borrowerRepository.save(any(Borrower.class))).thenReturn(update);

        Borrower result = borrowerService.updateBorrower(1L, update);

        assertEquals("John Updated", result.getName());
        assertEquals("john.new@example.com", result.getEmail());
    }

    @Test
    void deleteBorrower_Success() {
        Borrower borrower = new Borrower(1L, "John Doe", "john@example.com");
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));

        borrowerService.deleteBorrower(1L);

        verify(borrowerRepository, times(1)).delete(borrower);
    }
}
