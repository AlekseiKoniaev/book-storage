package ru.koniaev.bookstorage.service;

import java.util.List;

public interface EntityService<I, E> {
    
    boolean insert(Object...args);
    E getById(I id);
    List<E> getAll();
    int update(I id, Object...args);
    int deleteById(I id);
    void deleteAll();
}
