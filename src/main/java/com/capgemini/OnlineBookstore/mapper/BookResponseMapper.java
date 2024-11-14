package com.capgemini.OnlineBookstore.mapper;

import com.capgemini.OnlineBookstore.dto.Book;
import com.capgemini.OnlineBookstore.model.BookEntity;

public class BookResponseMapper {
    public Book map(BookEntity bookEntity){
        return Book.builder()
                .price(bookEntity.getPrice())
                .bookid(bookEntity.getId())
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .build();
    }
}
