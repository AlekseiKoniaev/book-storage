package ru.koniaev.bookstorage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.koniaev.bookstorage.exception.GenreNotFoundException;
import ru.koniaev.bookstorage.exception.WrongInputParametersException;
import ru.koniaev.bookstorage.model.Genre;
import ru.koniaev.bookstorage.repository.GenreRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository repository;
    
    
    public Genre create(Genre genre) {
        try {
            return repository.save(genre);
        } catch (IllegalArgumentException ex) {
            throw new WrongInputParametersException("Wrong parameters for genre. Try again.");
        }
    }
    
    public Genre getById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new GenreNotFoundException("Genre not found. Try another Id."));
    }
    
    public List<Genre> getAll() {
        Iterator<Genre> iterator = repository.findAll().iterator();
        List<Genre> genres = new ArrayList<>();
        while (iterator.hasNext()) {
            genres.add(iterator.next());
        }
        
        return genres;
    }
    
    public Genre update(Integer id, Genre genre) {
        if (!repository.existsById(id)) {
            throw new GenreNotFoundException("Genre not found. Try another Id.");
        }
        genre.setId(id);
        
        return repository.save(genre);
    }
    
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
    
    public void deleteAll() {
        repository.deleteAll();
    }
}
