package ru.koniaev.bookstorage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import ru.koniaev.bookstorage.model.Author;
import ru.koniaev.bookstorage.service.AuthorService;

import java.sql.Date;
import java.util.List;

@Tag(name = "author", description = "The author API")
@RestController
@RequestMapping("/api/author")
public class AuthorController {
    
    private final AuthorService service;
    
    public AuthorController(AuthorService service) {
        this.service = service;
    }
    
    
    @Operation(
            description = "Create new author",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Invalid input", responseCode = "400")})
    @PostMapping("/")
    public ResponseEntity<Response> insert(
            @Parameter @RequestParam("firstName") String firstName,
            @Parameter @RequestParam("secondName") String secondName,
            @Parameter @RequestParam("birthday") Date birthday) {
        
        boolean result = service.insert(firstName, secondName, birthday);
        
        if (result) {
            return new ResponseEntity<>(new Response(true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response(false), HttpStatus.BAD_REQUEST);
        }
        
    }
    
    
    @Operation(
            description = "Get author by *id*",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Author.class))),
                    @ApiResponse(description = "Author not found", responseCode = "404")})
    @GetMapping("/{id}")
    public ResponseEntity<Author> get(@Parameter @PathVariable int id) {
        
        Author author = service.getById(id);
        
        if (author == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(author, HttpStatus.OK);
        }
    }
    
    
    @Operation(
            description = "Get all authors",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = "array", implementation = Author.class))),
                    @ApiResponse(description = "Authors not found", responseCode = "404")})
    @GetMapping("/")
    public ResponseEntity<List<Author>> list() {
        
        List<Author> authorList = service.getAll();
        
        if (authorList.isEmpty()) {
            return new ResponseEntity<>(authorList, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(authorList, HttpStatus.OK);
        }
    }
    
    
    @Operation(
            description = "Edit author by *id*",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Author not found", responseCode = "404")})
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(
            @Parameter @PathVariable int id,
            @Parameter @RequestParam(value = "firstName", required = false, defaultValue = "null") String firstName,
            @Parameter @RequestParam(value = "secondName", required = false, defaultValue = "null") String secondName,
            @Parameter @RequestParam(value = "birthday", required = false, defaultValue = "null") Date birthday) {
        
        int result = service.update(id, firstName, secondName, birthday);
        
        if (result == 0) {
            return new ResponseEntity<>(new Response(false), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new IdResponse(id), HttpStatus.OK);
        }
    }
    
    
    @Operation(
            description = "Delete author by *id*",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "204"),
                    @ApiResponse(description = "Author not found", responseCode = "404")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@Parameter @PathVariable int id) {
        
        int result = service.deleteById(id);
        
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
        service.deleteAll();
        return new ResponseEntity<>(new Response(true), HttpStatus.NO_CONTENT);
    }
}
