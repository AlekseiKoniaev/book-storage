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
import ru.koniaev.bookstorage.model.Genre;
import ru.koniaev.bookstorage.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/genre")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService service;
    
    
    @PostMapping("/")
    public Genre create(Genre genre) {
        return service.create(genre);
    }
    
    
    @GetMapping("/{id}")
    public Genre get(@PathVariable int id) {
        return service.getById(id);
    }
    
    
    @GetMapping("/")
    public List<Genre> list() {
        return service.getAll();
    }
    
    
    @PutMapping("/{id}")
    public Genre update(@PathVariable int id, Genre genre) {
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
