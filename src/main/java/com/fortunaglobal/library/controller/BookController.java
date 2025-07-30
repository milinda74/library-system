package com.fortunaglobal.library.controller;

import com.fortunaglobal.library.model.Book;
import com.fortunaglobal.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

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
