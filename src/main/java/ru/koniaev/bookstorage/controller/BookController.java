package ru.koniaev.bookstorage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.koniaev.bookstorage.model.Book;
import ru.koniaev.bookstorage.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService service;
    
    
    @PostMapping("/")
    public Book create(Book book) {
        return service.create(book);
    }
    
    
    @GetMapping("/{id}")
    public Book get(@PathVariable int id) {
        return service.getById(id);
    }
    
    
    @GetMapping("/")
    public List<Book> list() {
        return service.getAll();
    }
    
    
    @PutMapping("/{id}")
    public Book update(@PathVariable int id, Book genre) {
        return service.update(id, genre);
    }
    
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.deleteById(id);
    }
    
    
    @DeleteMapping("/")
    public void deleteAll() {
        service.deleteAll();
    }
}
