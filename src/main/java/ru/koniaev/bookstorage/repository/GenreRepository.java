package ru.koniaev.bookstorage.repository;

import ru.koniaev.bookstorage.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    
    void save(Genre genre);
    Optional<Genre> findById(int id);
    Optional<List<Genre>> findAll();
    void update(Genre genre);
    void delete(int id);
    void deleteAll();
}
