package com.capgemini.OnlineBookstore.service;


import com.capgemini.OnlineBookstore.dto.Book;
import com.capgemini.OnlineBookstore.exception.BookNotFoundException;
import com.capgemini.OnlineBookstore.model.BookEntity;
import com.capgemini.OnlineBookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final ModelMapper modelMapper;

    public List<Book> getAllBooks() {
        List<BookEntity> bookEntityList = bookRepository.findAll();
        if (bookEntityList.isEmpty()){
            throw new BookNotFoundException("No books found");
        }
        return bookEntityList.stream().map(bookEntity -> modelMapper.map(bookEntity, Book.class)).collect(Collectors.toList());
    }

    public Book saveBook(Book book) {
        BookEntity bookEntity = modelMapper.map(book, BookEntity.class);
        return  modelMapper.map(bookRepository.save(bookEntity), Book.class);
    }

    public Book getBookById(Long id) {
        BookEntity bookEntity = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("BookEntity not found for id "+ id));
        return modelMapper.map(bookEntity, Book.class);
    }

    public List<Book> getBookByAuthor(String author) {
        List<BookEntity> bookEntityList = bookRepository.findByAuthor(author);
        if (bookEntityList.isEmpty()){
            throw new BookNotFoundException("No books found for the author "+ author);
        }
        return bookEntityList.stream().map(bookEntity -> modelMapper.map(bookEntity, Book.class)).collect(Collectors.toList());
    }

    public Book getBookByTitle(String title) {
        BookEntity bookEntity = bookRepository.findByTitle(title);
        if (bookEntity == null){
            throw new BookNotFoundException("No book found with the title "+ title);
        }
        return modelMapper.map(bookEntity, Book.class);
    }
}
