package ru.koniaev.bookstorage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.koniaev.bookstorage.exception.BookNotFoundException;
import ru.koniaev.bookstorage.exception.WrongInputParametersException;
import ru.koniaev.bookstorage.model.Book;
import ru.koniaev.bookstorage.repository.BookRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;
    
    
    public Book create(Book book) {
        try {
            return repository.save(book);
        } catch (IllegalArgumentException ex) {
            throw new WrongInputParametersException("Wrong parameters for book. Try again.");
        }
    }
    
    public Book getById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new BookNotFoundException("Book not found. Try another Id."));
    }
    
    public List<Book> getAll() {
        Iterator<Book> iterator = repository.findAll().iterator();
        List<Book> books = new ArrayList<>();
        while (iterator.hasNext()) {
            books.add(iterator.next());
        }
        
        return books;
    }
    
    public Book update(Integer id, Book book) {
        if (!repository.existsById(id)) {
            throw new BookNotFoundException("Book not found. Try another Id.");
        }
        book.setId(id);
        
        return repository.save(book);
    }
    
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
    
    public void deleteAll() {
        repository.deleteAll();
    }
}
