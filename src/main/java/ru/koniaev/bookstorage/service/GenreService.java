package ru.koniaev.bookstorage.service;

import ru.koniaev.bookstorage.model.Genre;

import java.util.List;

public interface GenreService {
    
    boolean insert(String name);
    Genre getById(int id);
    List<Genre> getAll();
    int update(int id, String name);
    int deleteById(int id);
    void deleteAll();
    
}
