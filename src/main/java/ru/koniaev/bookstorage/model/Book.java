package ru.koniaev.bookstorage.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
    
    private int id;
    private String title;
    private int year;
    private int pageCount;
    private int authorId;
    private int genreId;
    
}
