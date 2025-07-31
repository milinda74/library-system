package com.fortunaglobal.library.controller;

import com.fortunaglobal.library.model.Book;
import com.fortunaglobal.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

@Tag(name = "Books", description = "Manage library books")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(
            summary = "Get all books",
            description = "Retrieve a list of all books in the library",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of books",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Book.class)))
                    )
            }
    )


    /**
     * Register a new book
     */
    @PostMapping
    public ResponseEntity<Book> registerBook(@Valid @RequestBody Book book) {
        Book newBook = bookService.registerBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }

    /**
     * Get all books
     */
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }
}
