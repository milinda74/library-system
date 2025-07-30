package com.fortunaglobal.library.repository;

import com.fortunaglobal.library.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {

    /**
     * Find borrower by email (assumed unique)
     *
     * @param email Email address to search for
     * @return Optional containing borrower if found
     */
    Optional<Borrower> findByEmail(String email);

    /**
     * Check if a borrower with the same email exists (excluding current borrower)
     * Useful for update operations
     *
     * @param email Email to check
     * @param excludeId Borrower ID to exclude (e.g., current borrower being updated)
     * @return true if duplicate email exists
     */
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Borrower b " +
            "WHERE b.email = :email " +
            "AND b.id <> :excludeId")
    boolean existsByEmailExcludingId(
            @Param("email") String email,
            @Param("excludeId") Long excludeId);

    /**
     * Check if email exists (for new borrower creation)
     *
     * @param email Email to check
     * @return true if email already exists
     */
    default boolean emailExists(String email) {
        return findByEmail(email).isPresent();
    }
}
