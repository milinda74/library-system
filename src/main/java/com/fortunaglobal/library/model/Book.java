package com.fortunaglobal.library.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Library book entity")
@Entity
@Data
@NoArgsConstructor
public class Book {
    @Schema(description = "Unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "ISBN number (unique identifier for book editions)",
            example = "978-3-16-148410-0",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String isbn;

    @Schema(description = "Book title", example = "Effective Java", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String title;

    @Schema(description = "Book author", example = "Joshua Bloch", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String author;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public Book(Long id, String isbn, String title, String author) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }
}
