package ru.koniaev.bookstorage.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.koniaev.bookstorage.model.Author;
import ru.koniaev.bookstorage.repository.EntityRepository;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    private static final int ID = 1;

    @Mock
    private EntityRepository<Author, Integer> repository;
    
    @InjectMocks
    private AuthorServiceImpl service;
    
    @Test
    void insertAuthor_shouldCallRepository() {
        Author author = createAuthor();
        String firstName = author.getFirstName();
        String secondName = author.getSecondName();
        Date birthday = author.getBirthday();
        
        service.insert(firstName, secondName, birthday);
        
        verify(repository).save(author);
    }
    
    @Test
    void getById_shouldCallRepository() {
        final Author expected = mock(Author.class);
        when(repository.findById(ID)).thenReturn(expected);

        final Author actual = service.getById(ID);
    
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(repository).findById(ID);
    }
    
    @Test
    void getAll_shouldCallRepository() {
        final List<Author> expected = mock(List.class);
        when(repository.findAll()).thenReturn(expected);
        
        final List<Author> actual = service.getAll();
        
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(repository).findAll();
    }
    
    @Test
    void update_shouldCallRepository() {
        Author author = createAuthor();
        String firstName = author.getFirstName();
        String secondName = author.getSecondName();
        Date birthday = author.getBirthday();
        when(repository.findById(ID)).thenReturn(author);
        
        int actual = service.update(ID, firstName, secondName, birthday);
        
        assertEquals(ID, actual);
        verify(repository).findById(ID);
    }
    
    @Test
    void deleteById_shouldCallRepository() {
        final Author author = mock(Author.class);
        when(repository.findById(ID)).thenReturn(author);
        
        int actual = service.deleteById(ID);
    
        assertEquals(ID, actual);
        verify(repository).delete(ID);
    }
    
    @Test
    void deleteAll_shouldCallRepository() {
        service.deleteAll();
        
        verify(repository).deleteAll();
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
