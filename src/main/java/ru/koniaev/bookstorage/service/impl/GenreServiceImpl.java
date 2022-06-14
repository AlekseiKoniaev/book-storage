package ru.koniaev.bookstorage.service.impl;

import org.springframework.stereotype.Service;
import ru.koniaev.bookstorage.model.Genre;
import ru.koniaev.bookstorage.repository.EntityRepository;
import ru.koniaev.bookstorage.service.EntityService;

import java.util.List;

@Service
public class GenreServiceImpl implements EntityService<Genre, Integer> {
    
    private final EntityRepository<Genre, Integer> genreRepository;
    
    public GenreServiceImpl(EntityRepository<Genre, Integer> genreRepository) {
        this.genreRepository = genreRepository;
    }
    
    
    @Override
    public boolean insert(Object...args) {
    
        String name;
        try {
            name = (String) args[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
    
        if (name == null || name.isBlank()) {
            return false;
        }
    
        Genre genre = new Genre();
        genre.setName(name);
    
        genreRepository.save(genre);
    
        return true;
    }
    
    @Override
    public Genre getById(Integer id) {
        return genreRepository.findById(id);
    }
    
    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }
    
    @Override
    public int update(Integer id, Object...args) {
    
        String name;
        try {
            name = (String) args[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    
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
    public int deleteById(Integer id) {
        
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
