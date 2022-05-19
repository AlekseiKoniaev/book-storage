package ru.koniaev.bookstorage.repository;

import java.util.List;
import java.util.Optional;

public interface EntityRepository<T> {
    
    void save(T t);
    Optional<T> findById(int id);
    Optional<List<T>> findAll();
    void update(T author);
    void delete(int id);
    void deleteAll();
    
}
