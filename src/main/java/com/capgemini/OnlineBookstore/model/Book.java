package com.capgemini.OnlineBookstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Title cannot be empty")
    private String title;
    @NotEmpty(message = "Author cannot be empty")
    private String author;
    private double price;
}