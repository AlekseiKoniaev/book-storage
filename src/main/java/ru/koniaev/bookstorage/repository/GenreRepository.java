package ru.koniaev.bookstorage.repository;

import ru.koniaev.bookstorage.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    
    boolean save(Genre genre);
    Genre findById(int id);
    List<Genre> findAll();
    boolean update(Genre genre);
    void delete(int id);
    void deleteAll();
}
