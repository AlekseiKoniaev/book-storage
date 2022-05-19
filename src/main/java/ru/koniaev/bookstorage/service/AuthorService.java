package ru.koniaev.bookstorage.service;

import ru.koniaev.bookstorage.model.Author;

import java.sql.Date;
import java.util.List;

public interface AuthorService {
    
    boolean insert(String firstName, String secondName, Date birthday);
    Author getById(int id);
    List<Author> getAll();
    int update(int id, String firstName, String secondName, Date birthday);
    int deleteById(int id);
    void deleteAll();
    
}
