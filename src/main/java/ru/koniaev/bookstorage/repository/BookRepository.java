package ru.koniaev.bookstorage.repository;

import ru.koniaev.bookstorage.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    
    boolean save(Book book);
    Book findById(int id);
    List<Book> findAll();
    boolean update(Book book);
    void delete(int id);
    void deleteAll();
    
}
