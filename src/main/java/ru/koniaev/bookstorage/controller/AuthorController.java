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
import ru.koniaev.bookstorage.model.Author;
import ru.koniaev.bookstorage.service.AuthorService;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    
    private final AuthorService service;
    
    public AuthorController(AuthorService service) {
        this.service = service;
    }
    
    
    @PostMapping("/")
    public ResponseEntity<Response> insert(
            @RequestParam("firstName") String firstName,
            @RequestParam("secondName") String secondName,
            @RequestParam("birthday") Date birthday) {
        
        boolean result = service.insert(firstName, secondName, birthday);
        
        return new ResponseEntity<>(new Response(result), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Author> get(@PathVariable int id) {
    
        Author author = service.getById(id);
        
        if (author == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return new ResponseEntity<>(author, HttpStatus.OK);
        }
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Author>> list() {
        
        List<Author> authorList = service.getAll();
        
        if (authorList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return new ResponseEntity<>(authorList, HttpStatus.OK);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(
            @PathVariable int id,
            @RequestParam(value = "firstName", required = false, defaultValue = "") String firstName,
            @RequestParam(value = "secondName", required = false, defaultValue = "") String secondName,
            @RequestParam(value = "birthday", required = false, defaultValue = "null") Date birthday) {
        
        int result = service.update(id, firstName, secondName, birthday);
        
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return new ResponseEntity<>(new SuccessResponse(id), HttpStatus.OK);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable int id) {
        
        int result = service.deleteById(id);
        
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return new ResponseEntity<>(new SuccessResponse(id), HttpStatus.NO_CONTENT);
        }
    }
    
    @DeleteMapping("/")
    public ResponseEntity<Response> deleteAll() {
        service.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}