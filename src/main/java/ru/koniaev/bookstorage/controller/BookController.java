package ru.koniaev.bookstorage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import ru.koniaev.bookstorage.service.EntityService;

import java.util.List;

@Tag(name = "book", description = "The book API")
@RestController
@RequestMapping("/api/book")
public class BookController {
    
    private final EntityService<Integer, Book> bookService;
    
    public BookController(EntityService<Integer, Book> bookService) {
        this.bookService = bookService;
    }
    
    
    @Operation(
            description = "Create new book",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Invalid input", responseCode = "400")})
    @PostMapping("/")
    public ResponseEntity<Response> insert(
            @Parameter @RequestParam("title") String title,
            @Parameter @RequestParam("year") int year,
            @Parameter @RequestParam("pageCount") int pageCount,
            @Parameter @RequestParam("authorId") int authorId,
            @Parameter @RequestParam("genreId") int genreId) {
        
        boolean result = bookService.insert(title, year, pageCount, authorId, genreId);
        
        return new ResponseEntity<>(new Response(result), HttpStatus.OK);
    }
    
    
    @Operation(
            description = "Get book by id",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Book.class))),
                    @ApiResponse(description = "Book not found", responseCode = "404")})
    @GetMapping("/{id}")
    public ResponseEntity<Book> get(@Parameter @PathVariable int id) {
        
        Book book = bookService.getById(id);
        
        if (book == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
        
    }
    
    
    @Operation(
            description = "Get all books",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Book.class)))),
                    @ApiResponse(description = "Books not found", responseCode = "404")})
    @GetMapping("/")
    public ResponseEntity<List<Book>> list() {
        
        List<Book> bookList = bookService.getAll();
        
        if (bookList.isEmpty()) {
            return new ResponseEntity<>(bookList, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        }
    }
    
    
    @Operation(
            description = "Edit book by id",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Book not found", responseCode = "404")})
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(
            @Parameter @PathVariable int id,
            @Parameter @RequestParam(value = "title", required = false, defaultValue = "null") String title,
            @Parameter @RequestParam(value = "year", required = false, defaultValue = "0") int year,
            @Parameter @RequestParam(value = "pageCount", required = false, defaultValue = "0") int pageCount,
            @Parameter @RequestParam(value = "authorId", required = false, defaultValue = "0") int authorId,
            @Parameter @RequestParam(value = "genreId", required = false, defaultValue = "0") int genreId) {
    
        int result = bookService.update(id, title, year, pageCount, authorId, genreId);
    
        if (result == 0) {
            return new ResponseEntity<>(new Response(false), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new IdResponse(id), HttpStatus.OK);
        }
    }
    
    
    @Operation(
            description = "Delete book by id",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "204"),
                    @ApiResponse(description = "Book not found", responseCode = "404")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@Parameter @PathVariable int id) {
        
        int result = bookService.deleteById(id);
        
        if (result == 0) {
            return new ResponseEntity<>(new Response(false), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new IdResponse(id), HttpStatus.NO_CONTENT);
        }
    }
    
    
    @Operation(
            description = "Clear table",
            responses = {@ApiResponse(description = "OK", responseCode = "204")})
    @DeleteMapping("/")
    public ResponseEntity<Response> deleteAll() {
        bookService.deleteAll();
        return new ResponseEntity<>(new Response(true), HttpStatus.NO_CONTENT);
    }
}
