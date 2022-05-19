package ru.koniaev.bookstorage.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.koniaev.bookstorage.api.response.Response;
import ru.koniaev.bookstorage.api.response.SuccessResponse;
import ru.koniaev.bookstorage.model.Author;
import ru.koniaev.bookstorage.service.AuthorService;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {
    
    private final static int ID = 1;
    
    @Mock
    private AuthorService service;
    
    @InjectMocks
    private AuthorController controller;
    
    @Test
    void insert() {
        Author author = createAuthor();
        String firstName = author.getFirstName();
        String secondName = author.getSecondName();
        Date birthday = author.getBirthday();
        when(service.insert(firstName, secondName, birthday)).thenReturn(true);
        final ResponseEntity<Response> expected = new ResponseEntity<>(
                new Response(true), HttpStatus.OK);
        
        ResponseEntity<Response> actual = controller.insert(firstName, secondName, birthday);
    
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(service).insert(firstName, secondName, birthday);
    }
    
    @Test
    void get() {
        Author author = mock(Author.class);
        when(service.getById(ID)).thenReturn(author);
        final ResponseEntity<Author> expected = new ResponseEntity<>(author, HttpStatus.OK);
    
        ResponseEntity<Author> actual = controller.get(ID);
    
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(service).getById(ID);
    }
    
    @Test
    void list() {
        List<Author> authorList = mock(List.class);
        when(service.getAll()).thenReturn(authorList);
        final ResponseEntity<List<Author>> expected = new ResponseEntity<>(
                authorList, HttpStatus.OK);
    
        ResponseEntity<List<Author>> actual = controller.list();
    
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(service).getAll();
    }
    
    @Test
    void update() {
        Author author = createAuthor();
        String firstName = author.getFirstName();
        String secondName = author.getSecondName();
        Date birthday = author.getBirthday();
        when(service.update(ID, firstName, secondName, birthday)).thenReturn(ID);
        final ResponseEntity<Response> expected = new ResponseEntity<>(
                new SuccessResponse(ID), HttpStatus.OK);
    
        ResponseEntity<Response> actual = controller.update(ID, firstName, secondName, birthday);
    
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(service).update(ID, firstName, secondName, birthday);
    }
    
    @Test
    void delete() {
        when(service.deleteById(ID)).thenReturn(ID);
        final ResponseEntity<Response> expected = new ResponseEntity<>(
                new SuccessResponse(ID), HttpStatus.NO_CONTENT);
    
        ResponseEntity<Response> actual = controller.delete(ID);
    
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(service).deleteById(ID);
    }
    
    @Test
    void deleteAll() {
        final ResponseEntity<Response> expected = ResponseEntity
                .status(HttpStatus.NO_CONTENT).body(null);
        
        ResponseEntity<Response> actual = controller.deleteAll();
    
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(service).deleteAll();
    }
    
    private Author createAuthor() {
        Author author = new Author();
        author.setId(ID);
        author.setFirstName("Alex");
        author.setSecondName("Black");
        author.setBirthday(Date.valueOf("1987-04-13"));
        
        return author;
    }
}
