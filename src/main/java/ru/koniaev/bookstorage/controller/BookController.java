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
import ru.koniaev.bookstorage.api.response.IdResponse;
import ru.koniaev.bookstorage.api.response.Response;
import ru.koniaev.bookstorage.model.Book;
import ru.koniaev.bookstorage.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    
    private final BookService bookService;
    
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    
    @PostMapping("/")
    public ResponseEntity<Response> insert(
            @RequestParam("title") String title,
            @RequestParam("year") int year,
            @RequestParam("pageCount") int pageCount,
            @RequestParam("authorId") int authorId,
            @RequestParam("genreId") int genreId) {
        
        boolean result = bookService.insert(title, year, pageCount, authorId, genreId);
        
        return new ResponseEntity<>(new Response(result), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Book> get(@PathVariable int id) {
        
        Book book = bookService.getById(id);
        
        if (book == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
        
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Book>> list() {
        
        List<Book> bookList = bookService.getAll();
        
        if (bookList.isEmpty()) {
            return new ResponseEntity<>(bookList, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(
            @PathVariable int id,
            @RequestParam(value = "title", required = false, defaultValue = "null") String title,
            @RequestParam(value = "year", required = false, defaultValue = "0") int year,
            @RequestParam(value = "pageCount", required = false, defaultValue = "0") int pageCount,
            @RequestParam(value = "authorId", required = false, defaultValue = "0") int authorId,
            @RequestParam(value = "genreId", required = false, defaultValue = "0") int genreId) {
    
        int result = bookService.update(id, title, year, pageCount, authorId, genreId);
    
        if (result == 0) {
            return new ResponseEntity<>(new Response(false), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new IdResponse(id), HttpStatus.OK);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable int id) {
        
        int result = bookService.deleteById(id);
        
        if (result == 0) {
            return new ResponseEntity<>(new Response(false), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new IdResponse(id), HttpStatus.NO_CONTENT);
        }
    }
    
    @DeleteMapping("/")
    public ResponseEntity<Response> deleteAll() {
        bookService.deleteAll();
        return new ResponseEntity<>(new Response(true), HttpStatus.NO_CONTENT);
    }
}
