package com.capgemini.OnlineBookstore.controller;

import com.capgemini.OnlineBookstore.dto.Book;
import com.capgemini.OnlineBookstore.exception.BookNotFoundException;
import com.capgemini.OnlineBookstore.exception.InvalidRequestException;
import com.capgemini.OnlineBookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@Valid @RequestBody Book book) {
        if (book.getTitle() == null || book.getAuthor() == null || "".equals(book.getTitle()) || "".equals(book.getAuthor())) {
            throw new InvalidRequestException("BookEntity title and author are required");
        }
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.ok(savedBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/title")
    public ResponseEntity<Book> getBookByTitle(@RequestParam String title) {
        if(title == null || "".equals(title)){
            throw new InvalidRequestException("Title cannot be null or empty");
        }
        Book book = bookService.getBookByTitle(title);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/author")
    public ResponseEntity<List<Book>> getBookByAuthor(@RequestParam String author) {
        if(author == null || "".equals(author)){
            throw new InvalidRequestException("Title cannot be null or empty");
        }
        List<Book> books = bookService.getBookByAuthor(author);
        return ResponseEntity.ok(books);
    }
}