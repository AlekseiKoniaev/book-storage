package ru.koniaev.bookstorage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.koniaev.bookstorage.api.response.Response;
import ru.koniaev.bookstorage.api.response.SuccessResponse;
import ru.koniaev.bookstorage.model.Genre;
import ru.koniaev.bookstorage.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/genre")
public class GenreController {
    
    private final GenreService genreService;
    
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }
    
    
    @PostMapping("/")
    public ResponseEntity<Response> insert(@RequestParam("name") String name) {
        boolean result = genreService.add(name);
        return new ResponseEntity<>(new Response(result), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Genre> get(@PathVariable int id) {
    
        Genre genre = genreService.getById(id);
        
        if (genre == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return new ResponseEntity<>(genre, HttpStatus.OK);
        }
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Genre>> list() {
        
        List<Genre> authorList = genreService.getAll();
        
        if (authorList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return new ResponseEntity<>(authorList, HttpStatus.OK);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(
            @PathVariable int id,
            @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        
        int result = genreService.update(id, name);
        
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return new ResponseEntity<>(new SuccessResponse(id), HttpStatus.OK);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable int id) {
        
        int result = genreService.deleteById(id);
        
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return new ResponseEntity<>(new SuccessResponse(id), HttpStatus.NO_CONTENT);
        }
    }
    
    @DeleteMapping("/")
    public ResponseEntity<Response> deleteAll() {
        genreService.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
