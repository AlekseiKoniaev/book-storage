package ru.koniaev.bookstorage.repository.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import ru.koniaev.bookstorage.model.Author;
import ru.koniaev.bookstorage.repository.EntityRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(value = "/sql/author/author-before.sql", executionPhase = BEFORE_TEST_METHOD)
public class AuthorRepositoryTest {
    
    private final List<Author> preparedAuthorList = createAuthors();
    
    @Autowired
    private EntityRepository<Integer, Author> repository;
    
    @Test
    void save_shouldSaveAuthor_whenFieldsCorrectAndNotExists() {
        int expectedCount = 1;
        Author author = preparedAuthorList.get(0);
        int preSaveCount = repository.findAll().size();
        
        boolean actualReturn = repository.save(author);
        int postSaveCount = repository.findAll().size();
        int actualCount = postSaveCount - preSaveCount;
    
        assertTrue(actualReturn);
        assertEquals(expectedCount, actualCount);
    }
    
    @Test
    @Sql(value = "/sql/author/create-author-before.sql", executionPhase = BEFORE_TEST_METHOD)
    void save_shouldReturnFalse_whenExists() {
        int expected = 0;
        Author author = preparedAuthorList.get(0);
        int preSaveCount = repository.findAll().size();
    
        boolean actualReturn = repository.save(author);
        int postSaveCount = repository.findAll().size();
        int actual = postSaveCount - preSaveCount;
    
        assertFalse(actualReturn);
        assertEquals(expected, actual);
    }
    
    @Test
    void save_shouldSaveAuthor_whenFieldsNotCorrect() {
        int expectedCount = 0;
        Author author = preparedAuthorList.get(0);
        author.setFirstName(null);
        int preSaveCount = repository.findAll().size();
        
        boolean actualReturn = repository.save(author);
        int postSaveCount = repository.findAll().size();
        int actualCount = postSaveCount - preSaveCount;
        
        assertFalse(actualReturn);
        assertEquals(expectedCount, actualCount);
    }
    
    
    @Test
    @Sql(value = "/sql/author/create-author-before.sql", executionPhase = BEFORE_TEST_METHOD)
    void findById_shouldFindAuthor_whenExists() {
        Author expected = preparedAuthorList.get(0);
        expected.setId(1);
        
        Author actual = repository.findById(expected.getId());
        
        assertNotNull(actual);
        assertEquals(expected, actual);
    }
    
    @Test
    void findById_shouldReturnNull_whenNotExists() {
        Author actual = repository.findById(1);
        
        assertNull(actual);
    }
    
    
    @Test
    @Sql(value = "/sql/author/create-author-before.sql", executionPhase = BEFORE_TEST_METHOD)
    void findAll_shouldFindAllAuthors_whenExists() {
        int expectedCount = 2;
        
        List<Author> foundAuthors = repository.findAll();
        int actualCount = foundAuthors.size();
    
        assertEquals(expectedCount, actualCount);
        assertTrue(preparedAuthorList.containsAll(foundAuthors));
    }
    
    @Test
    void findAll_shouldReturnEmptyList_whenNotExists() {
        List<Author> actual = repository.findAll();
        
        assertTrue(actual.isEmpty());
    }
    
    
    @Test
    @Sql(value = "/sql/author/create-author-before.sql", executionPhase = BEFORE_TEST_METHOD)
    void update_shouldUpdateAuthor_whenFieldsCorrectAndExists() {
        Author editedAuthor = preparedAuthorList.get(2);
        editedAuthor.setId(1);
    
        boolean actualReturn = repository.update(editedAuthor);
        Author updatedAuthor = repository.findById(editedAuthor.getId());
        
        assertTrue(actualReturn);
        assertNotNull(updatedAuthor);
        assertEquals(editedAuthor, updatedAuthor);
    }
    
    @Test
    void update_shouldReturnTrue_whenNotExists() {
        Author editedAuthor = preparedAuthorList.get(2);
        editedAuthor.setId(1);
    
        boolean actualReturn = repository.update(editedAuthor);
        
        assertTrue(actualReturn);
    }
    
    @Test
    @Sql(value = "/sql/author/create-author-before.sql", executionPhase = BEFORE_TEST_METHOD)
    void update_shouldReturnFalse_whenFieldsNotCorrect() {
        Author expectedAuthor = preparedAuthorList.get(0);
        Author editedAuthor = preparedAuthorList.get(2);
        editedAuthor.setId(1);
        editedAuthor.setFirstName(null);
    
        boolean actualReturn = repository.update(editedAuthor);
        Author actualAuthor = repository.findById(editedAuthor.getId());
    
        assertFalse(actualReturn);
        assertNotNull(actualAuthor);
        assertEquals(expectedAuthor, actualAuthor);
    }
    
    
    @Test
    @Sql(value = "/sql/author/create-author-before.sql", executionPhase = BEFORE_TEST_METHOD)
    void delete_shouldDeleteAuthor_whenExists() {
        int expectedCount = 1;
        int preDeleteCount = repository.findAll().size();
        
        repository.delete(1);
        int postDeleteCount = repository.findAll().size();
        int actualCount = preDeleteCount - postDeleteCount;
        
        assertEquals(expectedCount, actualCount);
    }
    
    @Test
    void delete_shouldCompleteCorrectly_whenNotExists() {
        int expectedCount = 0;
        int preDeleteCount = repository.findAll().size();
        
        repository.delete(1);
        int postDeleteCount = repository.findAll().size();
        int actualCount = preDeleteCount - postDeleteCount;
        
        assertEquals(expectedCount, actualCount);
    }
    
    
    @Test
    @Sql(value = "/sql/author/create-author-before.sql", executionPhase = BEFORE_TEST_METHOD)
    void deleteAll_shouldDeleteAllAuthors_whenExist() {
        int expectedCount = 2;
        int preDeleteCount = repository.findAll().size();
    
        repository.deleteAll();
        int postDeleteCount = repository.findAll().size();
        int actualCount = preDeleteCount - postDeleteCount;
        
        assertEquals(expectedCount, actualCount);
    }
    
    @Test
    void deleteAll_shouldCompleteCorrectly_whenNotExist() {
        int expectedCount = 0;
        int preDeleteCount = repository.findAll().size();
        
        repository.deleteAll();
        int postDeleteCount = repository.findAll().size();
        int actualCount = preDeleteCount - postDeleteCount;
        
        assertEquals(expectedCount, actualCount);
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
