package ru.koniaev.bookstorage.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.koniaev.bookstorage.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
}
