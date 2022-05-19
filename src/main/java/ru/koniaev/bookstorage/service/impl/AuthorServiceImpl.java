package ru.koniaev.bookstorage.service.impl;

import org.springframework.stereotype.Service;
import ru.koniaev.bookstorage.model.Author;
import ru.koniaev.bookstorage.repository.AuthorRepository;
import ru.koniaev.bookstorage.service.AuthorService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    
    private final AuthorRepository authorRepository;
    
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    
    
    @Override
    public boolean insert(String firstName, String secondName, Date birthday) {
        
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
    public Author getById(int id) {
        return authorRepository.findById(id);
    }
    
    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }
    
    @Override
    public int update(int id, String firstName, String secondName, Date birthday) {
        
        Author author = authorRepository.findById(id);
    
        if (author == null) {
            return 0;
        } else {
            if (!firstName.isBlank()) {
                author.setFirstName(firstName);
            }
            if (!secondName.isBlank()) {
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
    public int deleteById(int id) {
    
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
