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
    public boolean add(String name) {
    
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
        Optional<Genre> genre = genreRepository.findById(id);
        return genre.orElse(null);
    }
    
    @Override
    public List<Genre> getAll() {
        var genreList = genreRepository.findAll();
        return genreList.orElse(new ArrayList<>());
    }
    
    @Override
    public int update(int id, String name) {
    
        Optional<Genre> genreOptional = genreRepository.findById(id);
    
        if (genreOptional.isEmpty()) {
            return 0;
        } else {
            Genre genre = genreOptional.get();
        
            if (!name.isBlank()) {
                genre.setName(name);
            }
    
            genreRepository.update(genre);
        
            return id;
        }
    }
    
    @Override
    public int deleteById(int id) {
        
        Optional<Genre> genre = genreRepository.findById(id);
    
        if (genre.isEmpty()) {
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
