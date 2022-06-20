package ru.koniaev.bookstorage.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.koniaev.bookstorage.model.Genre;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Integer> {
}
