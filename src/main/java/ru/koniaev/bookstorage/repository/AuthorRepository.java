package ru.koniaev.bookstorage.repository;

import ru.koniaev.bookstorage.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    
    void save(Author author);
    Author findById(int id);
    List<Author> findAll();
    void update(Author author);
    void delete(int id);
    void deleteAll();
}
