package com.capgemini.OnlineBookstore.controller;

import com.capgemini.OnlineBookstore.exception.BookNotFoundException;
import com.capgemini.OnlineBookstore.exception.InvalidRequestException;
import com.capgemini.OnlineBookstore.model.Book;
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
        if (books.isEmpty()){
            throw new BookNotFoundException("No books found");
        }
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@Valid @RequestBody Book book) {
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
        Book book = bookService.getBookByTitle(title);
        if (book == null){
            throw new BookNotFoundException("No books found with the title "+ title);
        }
        return ResponseEntity.ok(book);
    }

    @GetMapping("/author")
    public ResponseEntity<List<Book>> getBookByAuthor(@RequestParam String author) {
        List<Book> books = bookService.getBookByAuthor(author);
        if (books.isEmpty()){
            throw new BookNotFoundException("No books found for the author "+ author);
        }
        return ResponseEntity.ok(books);
    }
}