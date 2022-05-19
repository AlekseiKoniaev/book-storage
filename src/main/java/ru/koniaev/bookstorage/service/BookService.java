package ru.koniaev.bookstorage.service;

import org.springframework.stereotype.Service;
import ru.koniaev.bookstorage.model.Book;

import java.util.List;

public interface BookService {
    
    boolean insert(String title, int year, int pageCount, int authorId, int genreId);
    Book getById(int id);
    List<Book> getAll();
    int update(int id, String title, int year, int pageCount, int authorId, int genreId);
    int deleteById(int id);
    void deleteAll();
    
}
