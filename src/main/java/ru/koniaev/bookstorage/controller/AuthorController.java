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
import ru.koniaev.bookstorage.model.Author;
import ru.koniaev.bookstorage.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/api/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService service;
    
    
    @PostMapping("/")
    public Author create(Author author) {
        return service.create(author);
    }
    
    
    @GetMapping("/{id}")
    public Author get(@PathVariable int id) {
        return service.getById(id);
    }
    
    
    @GetMapping("/")
    public List<Author> list() {
        return service.getAll();
    }
    
    
    @PutMapping("/{id}")
    public Author update(@PathVariable int id, Author author) {
        return service.update(id, author);
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
