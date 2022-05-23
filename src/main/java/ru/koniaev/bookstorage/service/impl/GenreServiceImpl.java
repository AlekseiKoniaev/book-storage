package ru.koniaev.bookstorage.service.impl;

import org.springframework.stereotype.Service;
import ru.koniaev.bookstorage.model.Genre;
import ru.koniaev.bookstorage.repository.GenreRepository;
import ru.koniaev.bookstorage.service.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    
    private final GenreRepository genreRepository;
    
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }
    
    
    @Override
    public boolean insert(String name) {
    
        if (name == null || name.isBlank()) {
            return false;
        }
    
        Genre genre = new Genre();
        genre.setName(name);
    
        genreRepository.save(genre);
    
        return true;
    }
    
    @Override
    public Genre getById(int id) {
        return genreRepository.findById(id);
    }
    
    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }
    
    @Override
    public int update(int id, String name) {
    
        Genre genre = genreRepository.findById(id);
    
        if (genre == null) {
            return 0;
        } else {
            if (!name.isBlank()) {
                genre.setName(name);
            }
    
            genreRepository.update(genre);
        
            return id;
        }
    }
    
    @Override
    public int deleteById(int id) {
        
        Genre genre = genreRepository.findById(id);
    
        if (genre == null) {
            return 0;
        } else {
            genreRepository.delete(id);
            return id;
        }
    }
    
    @Override
    public void deleteAll() {
        genreRepository.deleteAll();
    }
}
