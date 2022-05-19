package ru.koniaev.bookstorage.repository;

import ru.koniaev.bookstorage.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    
    void save(Book book);
    Optional<Book> findById(int id);
    Optional<List<Book>> findAll();
    void update(Book book);
    void delete(int id);
    void deleteAll();
    
}
