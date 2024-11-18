package com.capgemini.OnlineBookstore.repository;

import com.capgemini.OnlineBookstore.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByAuthor(String author);
    BookEntity findByTitle(String title);
}