package ru.koniaev.bookstorage.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.koniaev.bookstorage.api.response.IdResponse;
import ru.koniaev.bookstorage.api.response.Response;
import ru.koniaev.bookstorage.model.Author;
import ru.koniaev.bookstorage.service.EntityService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {
    
    private final static int ID = 1;
    
    @Mock
    private EntityService<Integer, Author> service;
    
    @InjectMocks
    private AuthorController controller;
    
    @Test
    void insert_shouldSaveAuthorAndReturnTrueResponse() {
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
    void insert_shouldNotSaveAuthorAndReturnFalseResponse() {
        Author author = createAuthor();
        String firstName = author.getFirstName();
        String secondName = author.getSecondName();
        Date birthday = author.getBirthday();
        when(service.insert(firstName, secondName, birthday)).thenReturn(false);
        final ResponseEntity<Response> expected = new ResponseEntity<>(
                new Response(false), HttpStatus.BAD_REQUEST);
        
        ResponseEntity<Response> actual = controller.insert(firstName, secondName, birthday);
        
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(service).insert(firstName, secondName, birthday);
    }
    
    @Test
    void get_shouldGetAuthorAndReturnOkStatus() {
        Author author = mock(Author.class);
        when(service.getById(ID)).thenReturn(author);
        final ResponseEntity<Author> expected = new ResponseEntity<>(author, HttpStatus.OK);
    
        ResponseEntity<Author> actual = controller.get(ID);
    
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(service).getById(ID);
    }
    
    @Test
    void get_shouldNotGetAuthorAndReturnNotFoundStatus() {
        Author author = mock(Author.class);
        when(service.getById(ID)).thenReturn(null);
        final ResponseEntity<Author> expected = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        ResponseEntity<Author> actual = controller.get(ID);
        
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(service).getById(ID);
    }
    
    @Test
    void list_shouldGetListAuthorsAndReturnOkStatus() {
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
    void list_shouldGetEmptyListAndReturnNotFoundStatus() {
        List<Author> emptyList = new ArrayList<>();
        when(service.getAll()).thenReturn(emptyList);
        final ResponseEntity<List<Author>> expected = new ResponseEntity<>(
                emptyList, HttpStatus.NOT_FOUND);
        
        ResponseEntity<List<Author>> actual = controller.list();
        
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(service).getAll();
    }
    
    @Test
    void update_shouldReturnId_whenIdExistsAndCorrectParams() {
        Author author = createAuthor();
        String firstName = author.getFirstName();
        String secondName = author.getSecondName();
        Date birthday = author.getBirthday();
        when(service.update(ID, firstName, secondName, birthday)).thenReturn(ID);
        final ResponseEntity<Response> expected = new ResponseEntity<>(
                new IdResponse(ID), HttpStatus.OK);
    
        ResponseEntity<Response> actual = controller.update(ID, firstName, secondName, birthday);
    
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(service).update(ID, firstName, secondName, birthday);
    }
    
    @Test
    void update_shouldReturnFalse_whenIncorrectParams() {
        when(service.update(ID, null, null, null)).thenReturn(0);
        final ResponseEntity<Response> expected = new ResponseEntity<>(
                new Response(false), HttpStatus.NOT_FOUND);
        
        ResponseEntity<Response> actual = controller.update(ID, null, null, null);
        
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(service).update(ID, null, null, null);
    }
    
    @Test
    void update_shouldReturnFalse_whenIdNotExists() {
        Author author = createAuthor();
        String firstName = author.getFirstName();
        String secondName = author.getSecondName();
        Date birthday = author.getBirthday();
        when(service.update(ID, firstName, secondName, birthday)).thenReturn(0);
        final ResponseEntity<Response> expected = new ResponseEntity<>(
                new Response(false), HttpStatus.NOT_FOUND);
        
        ResponseEntity<Response> actual = controller.update(ID, firstName, secondName, birthday);
        
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(service).update(ID, firstName, secondName, birthday);
    }
    
    
    @Test
    void delete_shouldReturnId_whenIdExists() {
        when(service.deleteById(ID)).thenReturn(ID);
        final ResponseEntity<Response> expected = new ResponseEntity<>(
                new IdResponse(ID), HttpStatus.NO_CONTENT);
    
        ResponseEntity<Response> actual = controller.delete(ID);
    
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(service).deleteById(ID);
    }
    
    @Test
    void delete_shouldReturnFalse_whenIdNotExists() {
        when(service.deleteById(ID)).thenReturn(0);
        final ResponseEntity<Response> expected = new ResponseEntity<>(
                new Response(false), HttpStatus.NOT_FOUND);
        
        ResponseEntity<Response> actual = controller.delete(ID);
        
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(service).deleteById(ID);
    }
    
    
    @Test
    void deleteAll_shouldReturnNoContent() {
        final ResponseEntity<Response> expected =
                new ResponseEntity<>(new Response(true), HttpStatus.NO_CONTENT);
        
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
