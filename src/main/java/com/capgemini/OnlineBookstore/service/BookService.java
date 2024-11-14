package com.capgemini.OnlineBookstore.service;


import com.capgemini.OnlineBookstore.exception.BookNotFoundException;
import com.capgemini.OnlineBookstore.dto.Book;
import com.capgemini.OnlineBookstore.mapper.BookRequestMapper;
import com.capgemini.OnlineBookstore.mapper.BookResponseMapper;
import com.capgemini.OnlineBookstore.model.BookEntity;
import com.capgemini.OnlineBookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    BookResponseMapper responseMapper = new BookResponseMapper();

    BookRequestMapper requestMapper = new BookRequestMapper();

    public List<Book> getAllBooks() {
        List<BookEntity> bookEntityList = bookRepository.findAll();
        if (bookEntityList.isEmpty()){
            throw new BookNotFoundException("No books found");
        }
        return bookEntityList.stream().map(bookEntity -> responseMapper.map(bookEntity)).collect(Collectors.toList());
    }

    public Book saveBook(Book book) {
        BookEntity bookEntity = requestMapper.map(book);
        return  responseMapper.map(bookRepository.save(bookEntity));
    }

    public Book getBookById(Long id) {
        BookEntity bookEntity = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("BookEntity not found for id "+ id));
        return responseMapper.map(bookEntity);
    }

    public List<Book> getBookByAuthor(String author) {
        List<BookEntity> bookEntityList = bookRepository.findByAuthor(author);
        if (bookEntityList.isEmpty()){
            throw new BookNotFoundException("No books found for the author "+ author);
        }
        return bookEntityList.stream().map(bookEntity -> responseMapper.map(bookEntity)).collect(Collectors.toList());
    }

    public Book getBookByTitle(String title) {
        BookEntity bookEntity = bookRepository.findByTitle(title);
        if (bookEntity == null){
            throw new BookNotFoundException("No book found with the title "+ title);
        }
        return responseMapper.map(bookEntity);
    }
}
