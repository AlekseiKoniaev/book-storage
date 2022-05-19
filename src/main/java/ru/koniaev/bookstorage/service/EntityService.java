package ru.koniaev.bookstorage.service;

import java.util.List;

public interface EntityService<T> {
    
    boolean insert(Object...args);
    T getById(int id);
    List<T> getAll();
    int update(Object...args);
    int deleteById(int id);
    void deleteAll();
}
