package com.capgemini.OnlineBookstore.service;

import com.capgemini.OnlineBookstore.dto.Book;
import com.capgemini.OnlineBookstore.model.BookEntity;
import com.capgemini.OnlineBookstore.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DirtiesContext
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookService bookService;

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
        Book book = new Book(1L, "Da vinci code", "Dan Brown", 11);

        when(bookRepository.save(any(BookEntity.class))).thenReturn(bookEntity);

        when(modelMapper.map(book, BookEntity.class)).thenReturn(bookEntity);
        when(modelMapper.map(bookEntity, Book.class)).thenReturn(book);

        Book savedBook = bookService.saveBook(book);
        verify(bookRepository, times(1)).save(bookEntity);
        assertNotNull(savedBook);
        assertEquals("Da vinci code", savedBook.getTitle());
    }

    @Test
    public void testGetBookById() {
        Long bookId = 1L;
        BookEntity mockBookEntityEntity = new BookEntity(bookId, "Da vinci code", "Dan Brown", 11);
        Book bookDTO = new Book(1L, "Da vinci code", "Dan Brown", 11);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBookEntityEntity));
        when(modelMapper.map(mockBookEntityEntity, Book.class)).thenReturn(bookDTO);

        Book book = bookService.getBookById(bookId);
        verify(bookRepository, times(1)).findById(bookId);
        assertNotNull(book);
        assertEquals(11, book.getPrice());
    }

    @Test
    public void testGetBookByAuthor() {
        String author = "Dan Brown";
        BookEntity mockBookEntityEntity = new BookEntity(1L, "Da vinci code", author, 11);
        Book bookDTO = new Book(1L, "Da vinci code", "Dan Brown", 11);
        when(bookRepository.findByAuthor(author)).thenReturn(List.of(mockBookEntityEntity));
        when(modelMapper.map(mockBookEntityEntity, Book.class)).thenReturn(bookDTO);

        List<Book> books = bookService.getBookByAuthor(author);
        verify(bookRepository, times(1)).findByAuthor(author);
        assertNotNull(books);
        assertEquals(author, books.get(0).getAuthor());
    }

    @Test
    public void testGetBookByTitle() {
        String title = "Da vinci code";
        BookEntity mockBookEntityEntity = new BookEntity(1L, title, "Dan Brown", 11);
        Book bookDTO = new Book(1L, "Da vinci code", "Dan Brown", 11);

        when(bookRepository.findByTitle(title)).thenReturn(mockBookEntityEntity);
        when(modelMapper.map(mockBookEntityEntity, Book.class)).thenReturn(bookDTO);

        Book book = bookService.getBookByTitle(title);
        verify(bookRepository, times(1)).findByTitle(title);
        assertNotNull(book);
        assertEquals(title, book.getTitle());
    }
}