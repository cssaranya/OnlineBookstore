package com.capgemini.OnlineBookstore.controller;

import com.capgemini.OnlineBookstore.dto.Book;
import com.capgemini.OnlineBookstore.exception.InvalidRequestException;
import com.capgemini.OnlineBookstore.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/allBooks")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @PostMapping("/saveBook")
    public ResponseEntity<Book> saveBook(@Valid @RequestBody Book book) {
        if (book.getTitle() == null || book.getAuthor() == null || book.getTitle().isEmpty() || book.getAuthor().isEmpty()) {
            throw new InvalidRequestException("BookEntity title and author are required");
        }
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @GetMapping("/bookById/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/bookByTitle")
    public ResponseEntity<Book> getBookByTitle(@RequestParam String title) {
        if(title == null || title.isEmpty()){
            throw new InvalidRequestException("Title cannot be null or empty");
        }
        Book book = bookService.getBookByTitle(title);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/bookByAuthor")
    public ResponseEntity<List<Book>> getBookByAuthor(@RequestParam String author) {
        if(author == null || author.isEmpty()){
            throw new InvalidRequestException("Title cannot be null or empty");
        }
        List<Book> books = bookService.getBookByAuthor(author);
        return ResponseEntity.ok(books);
    }
}