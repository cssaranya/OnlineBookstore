package com.capgemini.OnlineBookstore.service;

import com.capgemini.OnlineBookstore.model.Book;
import com.capgemini.OnlineBookstore.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;

    @Test
    public void testGetAllBooks() {
        Book book1 = new Book(1L, "Da vinci code", "Dan Brown", 11);
        Book book2 = new Book(2L, "The monk who sold his Ferrari", "Robin Sharma", 8.6);
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1,book2));

        List<Book> books = bookService.getAllBooks();
        verify(bookRepository, times(1)).findAll();
        assertFalse(books.isEmpty());
        assertEquals(2,books.size());
    }

    @Test
    public void testSaveBook() {
        Book book = new Book(1L, "Da vinci code", "Dan Brown", 11);
        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookService.saveBook(book);
        verify(bookRepository, times(1)).save(book);
        assertNotNull(savedBook);
        assertEquals("Da vinci code", savedBook.getTitle());
    }

    @Test
    public void testGetBookById() {
        Long bookId = 1L;
        Book mockBook = new Book(bookId, "Da vinci code", "Dan Brown", 11);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));

        Book book = bookService.getBookById(bookId);
        verify(bookRepository, times(1)).findById(bookId);
        assertNotNull(book);
        assertEquals(11, book.getPrice());
    }

    @Test
    public void testGetBookByAuthor() {
        String author = "Dan Brown";
        Book mockBook = new Book(1L, "Da vinci code", author, 11);
        when(bookRepository.findByAuthor(author)).thenReturn(Arrays.asList(mockBook));

        List<Book> books = bookService.getBookByAuthor(author);
        verify(bookRepository, times(1)).findByAuthor(author);
        assertNotNull(books);
        assertEquals(author, books.get(0).getAuthor());
    }

    @Test
    public void testGetBookByTitle() {
        String title = "Da vinci code";
        Book mockBook = new Book(1L, title, "Dan Brown", 11);
        when(bookRepository.findByTitle(title)).thenReturn(mockBook);

        Book book = bookService.getBookByTitle(title);
        verify(bookRepository, times(1)).findByTitle(title);
        assertNotNull(book);
        assertEquals(title, book.getTitle());
    }
}