package ru.koniaev.bookstorage.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.koniaev.bookstorage.model.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {
}
