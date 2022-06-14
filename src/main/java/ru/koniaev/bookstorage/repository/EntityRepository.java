package ru.koniaev.bookstorage.repository;

import java.util.List;

public interface EntityRepository<E, I> {
    
    boolean save(E entity);
    E findById(I id);
    List<E> findAll();
    boolean update(E entity);
    void delete(I id);
    void deleteAll();
}
