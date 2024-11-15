package com.capgemini.OnlineBookstore.mapper;

import com.capgemini.OnlineBookstore.dto.Book;
import com.capgemini.OnlineBookstore.model.BookEntity;

public class BookRequestMapper {
    public BookEntity map(Book book){
        return BookEntity.builder()
                .price(book.getPrice())
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .build();
    }
}
