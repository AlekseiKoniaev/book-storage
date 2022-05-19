package ru.koniaev.bookstorage.repository.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import ru.koniaev.bookstorage.model.Author;
import ru.koniaev.bookstorage.repository.AuthorRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = "/sql/author-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AuthorRepositoryTest {
    
    private final List<Author> prepareAuthorList = createAuthors();
    
    @Autowired
    private AuthorRepository repository;
    
    @Test
    void save_shouldSaveAuthor() {
        int expected = 1;
        Author author = prepareAuthorList.get(0);
        int preSaveCount = repository.findAll().size();
        
        repository.save(author);
        int postSaveCount = repository.findAll().size();
        int actual = postSaveCount - preSaveCount;
        
        assertEquals(expected, actual);
    }
    
    @Test
    void findById_shouldSaveAndFindAuthor() {
        Author author = prepareAuthorList.get(0);
        repository.save(author);
        Author expected = repository.findAll().get(0);
        
        Author actual = repository.findById(expected.getId());
        
        assertNotNull(actual);
        assertEquals(expected, actual);
    }
    
    @Test
    void findAll_shouldSaveAndFindAllAuthors() {
        prepareAuthorList.forEach(author -> repository.save(author));
        int expectedCount = prepareAuthorList.size();
        
        List<Author> foundAuthors = repository.findAll();
        int actualCount = foundAuthors.size();
    
        assertEquals(expectedCount, actualCount);
        assertTrue(prepareAuthorList.containsAll(foundAuthors));
    }
    
    @Test
    void update_shouldSaveAndUpdateAuthor() {
        Author initialAuthor = prepareAuthorList.get(0);
        repository.save(initialAuthor);
        Author foundAuthor = repository.findAll().get(0);
        Author newAuthor = prepareAuthorList.get(1);
        newAuthor.setId(foundAuthor.getId());
        
        repository.update(newAuthor);
        Author updatedAuthor = repository.findById(newAuthor.getId());
        
        assertNotNull(foundAuthor);
        assertNotNull(updatedAuthor);
        assertNotEquals(foundAuthor, updatedAuthor);
        assertEquals(newAuthor, updatedAuthor);
    }
    
    @Test
    void delete_shouldSaveAndDeleteAuthor() {
        int expected = 1;
        Author author = prepareAuthorList.get(0);
        repository.save(author);
        var foundAuthors = repository.findAll();
        int preDeleteCount = foundAuthors.size();
        int id = foundAuthors.get(0).getId();
        
        repository.delete(id);
        int postDeleteCount = repository.findAll().size();
        int actual = preDeleteCount - postDeleteCount;
        
        assertEquals(expected, actual);
    }
    
    @Test
    void delete_shouldMultipleSaveAndDeleteAllAuthors() {
        int expected = prepareAuthorList.size();
        prepareAuthorList.forEach(author -> repository.save(author));
        int preDeleteCount = repository.findAll().size();
        
        repository.deleteAll();
        int postDeleteCount = repository.findAll().size();
        int actual = preDeleteCount - postDeleteCount;
        
        assertEquals(expected, actual);
    }
    
    private List<Author> createAuthors() {
        List<Author> list = new ArrayList<>();
        
        Author author1 = new Author();
        Author author2 = new Author();
        Author author3 = new Author();
        
        author1.setFirstName("Alex");
        author1.setSecondName("Black");
        author1.setBirthday(Date.valueOf("1987-04-13"));
        list.add(author1);
    
        author2.setFirstName("Mike");
        author2.setSecondName("Green");
        author2.setBirthday(Date.valueOf("1974-01-02"));
        list.add(author2);
    
        author3.setFirstName("Jess");
        author3.setSecondName("Lowsen");
        author3.setBirthday(Date.valueOf("1987-10-24"));
        list.add(author3);
    
        return list;
    }
}
