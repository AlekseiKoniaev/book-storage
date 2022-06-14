package ru.koniaev.bookstorage.service.impl;

import org.springframework.stereotype.Service;
import ru.koniaev.bookstorage.model.Author;
import ru.koniaev.bookstorage.repository.EntityRepository;
import ru.koniaev.bookstorage.service.EntityService;

import java.sql.Date;
import java.util.List;

@Service
public class AuthorServiceImpl implements EntityService<Author, Integer> {
    
    private final EntityRepository<Author, Integer> authorRepository;
    
    public AuthorServiceImpl(EntityRepository<Author, Integer> authorRepository) {
        this.authorRepository = authorRepository;
    }
    
    
    @Override
    public boolean insert(Object...args) {
    
        String firstName;
        String secondName;
        Date birthday;
        try {
            firstName = (String) args[0];
            secondName = (String) args[1];
            birthday = (Date) args[2];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
        
        if ((firstName == null || firstName.isBlank()) ||
                (secondName == null || secondName.isBlank()) || birthday == null) {
            return false;
        }
    
        Author author = new Author();
    
        author.setFirstName(firstName);
        author.setSecondName(secondName);
        author.setBirthday(birthday);
    
        authorRepository.save(author);
    
        return true;
    }
    
    @Override
    public Author getById(Integer id) {
        return authorRepository.findById(id);
    }
    
    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }
    
    @Override
    public int update(Integer id, Object...args) {
        
        Author author = authorRepository.findById(id);
    
        String firstName;
        String secondName;
        Date birthday;
        try {
            firstName = (String) args[0];
            secondName = (String) args[1];
            birthday = (Date) args[2];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    
        if (author == null || (firstName == null && secondName == null && birthday == null)) {
            return 0;
        } else {
            if (firstName != null && !firstName.isBlank()) {
                author.setFirstName(firstName);
            }
            if (secondName != null && !secondName.isBlank()) {
                author.setSecondName(secondName);
            }
            if (birthday != null) {
                author.setBirthday(birthday);
            }
    
            authorRepository.update(author);
        
            return id;
        }
    }
    
    @Override
    public int deleteById(Integer id) {
    
        Author author = authorRepository.findById(id);
        
        if (author == null) {
            return 0;
        } else {
            authorRepository.delete(id);
            return id;
        }
    }
    
    @Override
    public void deleteAll() {
        authorRepository.deleteAll();
    }
}
