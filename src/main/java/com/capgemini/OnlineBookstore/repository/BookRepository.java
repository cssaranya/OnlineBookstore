package com.capgemini.OnlineBookstore.repository;

import com.capgemini.OnlineBookstore.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByAuthor(String author);
    BookEntity findByTitle(String title);
}