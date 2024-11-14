package com.capgemini.OnlineBookstore.repository;

import com.capgemini.OnlineBookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(String author);
    Book findByTitle(String title);
}