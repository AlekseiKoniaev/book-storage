package ru.koniaev.bookstorage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.koniaev.bookstorage.exception.AuthorNotFoundException;
import ru.koniaev.bookstorage.exception.WrongInputParametersException;
import ru.koniaev.bookstorage.model.Author;
import ru.koniaev.bookstorage.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;
    
    
    public Author create(Author author) {
        try {
            return repository.save(author);
        } catch (IllegalArgumentException ex) {
            throw new WrongInputParametersException("Wrong parameters for author. Try again.");
        }
    }

    public Author getById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new AuthorNotFoundException("Author not found. Try another Id."));
    }

    public List<Author> getAll() {
        Iterator<Author> iterator = repository.findAll().iterator();
        List<Author> authors = new ArrayList<>();
        while (iterator.hasNext()) {
            authors.add(iterator.next());
        }
        
        return authors;
    }
    
    public Author update(Integer id, Author author) {
        if (!repository.existsById(id)) {
            throw new AuthorNotFoundException("Author not found. Try another Id.");
        }
        author.setId(id);
        
        return repository.save(author);
    }
    
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
