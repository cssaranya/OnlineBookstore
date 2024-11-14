package com.capgemini.OnlineBookstore.service;

import com.capgemini.OnlineBookstore.dto.Book;
import com.capgemini.OnlineBookstore.model.BookEntity;
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
public class BookEntityServiceTest {
    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;

    @Test
    public void testGetAllBooks() {
        BookEntity bookEntity1 = new BookEntity(1L, "Da vinci code", "Dan Brown", 11);
        BookEntity bookEntity2 = new BookEntity(2L, "The monk who sold his Ferrari", "Robin Sharma", 8.6);
        when(bookRepository.findAll()).thenReturn(Arrays.asList(bookEntity1, bookEntity2));

        List<Book> books = bookService.getAllBooks();
        verify(bookRepository, times(1)).findAll();
        assertFalse(books.isEmpty());
        assertEquals(2,books.size());
    }

    @Test
    public void testSaveBook() {
        BookEntity bookEntity = new BookEntity(1L, "Da vinci code", "Dan Brown", 11);
        when(bookRepository.save(bookEntity)).thenReturn(bookEntity);
        Book book = new Book(1L, "Da vinci code", "Dan Brown", 11);
        Book savedBook = bookService.saveBook(book);
        verify(bookRepository, times(1)).save(bookEntity);
        assertNotNull(savedBook);
        assertEquals("Da vinci code", savedBook.getTitle());
    }

    @Test
    public void testGetBookById() {
        Long bookId = 1L;
        BookEntity mockBookEntityEntity = new BookEntity(bookId, "Da vinci code", "Dan Brown", 11);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBookEntityEntity));

        Book book = bookService.getBookById(bookId);
        verify(bookRepository, times(1)).findById(bookId);
        assertNotNull(book);
        assertEquals(11, book.getPrice());
    }

    @Test
    public void testGetBookByAuthor() {
        String author = "Dan Brown";
        BookEntity mockBookEntityEntity = new BookEntity(1L, "Da vinci code", author, 11);
        when(bookRepository.findByAuthor(author)).thenReturn(Arrays.asList(mockBookEntityEntity));

        List<Book> books = bookService.getBookByAuthor(author);
        verify(bookRepository, times(1)).findByAuthor(author);
        assertNotNull(books);
        assertEquals(author, books.get(0).getAuthor());
    }

    @Test
    public void testGetBookByTitle() {
        String title = "Da vinci code";
        BookEntity mockBookEntityEntity = new BookEntity(1L, title, "Dan Brown", 11);
        when(bookRepository.findByTitle(title)).thenReturn(mockBookEntityEntity);

        Book book = bookService.getBookByTitle(title);
        verify(bookRepository, times(1)).findByTitle(title);
        assertNotNull(book);
        assertEquals(title, book.getTitle());
    }
}