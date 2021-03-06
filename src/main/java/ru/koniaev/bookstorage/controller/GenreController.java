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
import ru.koniaev.bookstorage.model.Genre;
import ru.koniaev.bookstorage.service.EntityService;

import java.util.List;

@Tag(name = "genre", description = "The genre API")
@RestController
@RequestMapping("/api/genre")
public class GenreController {
    
    private final EntityService<Integer, Genre> genreService;
    
    public GenreController(EntityService<Integer, Genre> genreService) {
        this.genreService = genreService;
    }
    
    
    @Operation(
            description = "Create new genre",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Invalid input", responseCode = "400")})
    @PostMapping("/")
    public ResponseEntity<Response> insert(@Parameter @RequestParam("name") String name) {
        boolean result = genreService.insert(name);
        return new ResponseEntity<>(new Response(result), HttpStatus.OK);
    }
    
    
    @Operation(
            description = "Get genre by id",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Genre.class))),
                    @ApiResponse(description = "Genre not found", responseCode = "404")})
    @GetMapping("/{id}")
    public ResponseEntity<Genre> get(@Parameter @PathVariable int id) {
    
        Genre genre = genreService.getById(id);
        
        if (genre == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(genre, HttpStatus.OK);
        }
    }
    
    
    @Operation(
            description = "Get all genres",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Genre.class)))),
                    @ApiResponse(description = "Genres not found", responseCode = "404")})
    @GetMapping("/")
    public ResponseEntity<List<Genre>> list() {
        
        List<Genre> authorList = genreService.getAll();
        
        if (authorList.isEmpty()) {
            return new ResponseEntity<>(authorList, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(authorList, HttpStatus.OK);
        }
    }
    
    
    @Operation(
            description = "Edit genre by id",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Genre not found", responseCode = "404")})
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(
            @Parameter @PathVariable int id,
            @Parameter @RequestParam(value = "name", required = false, defaultValue = "null") String name) {
        
        int result = genreService.update(id, name);
        
        if (result == 0) {
            return new ResponseEntity<>(new Response(false), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new IdResponse(id), HttpStatus.OK);
        }
    }
    
    
    @Operation(
            description = "Delete genre by id",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "204"),
                    @ApiResponse(description = "Genre not found", responseCode = "404")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@Parameter @PathVariable int id) {
        
        int result = genreService.deleteById(id);
        
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
        genreService.deleteAll();
        return new ResponseEntity<>(new Response(true), HttpStatus.NO_CONTENT);
    }
}
