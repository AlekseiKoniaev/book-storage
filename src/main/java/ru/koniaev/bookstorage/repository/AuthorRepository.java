package ru.koniaev.bookstorage.repository;

import ru.koniaev.bookstorage.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    
    boolean save(Author author);
    Author findById(int id);
    List<Author> findAll();
    boolean update(Author author);
    void delete(int id);
    void deleteAll();
}
