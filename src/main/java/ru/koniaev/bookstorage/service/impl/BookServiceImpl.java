package ru.koniaev.bookstorage.service.impl;

import org.springframework.stereotype.Service;
import ru.koniaev.bookstorage.model.Book;
import ru.koniaev.bookstorage.repository.BookRepository;
import ru.koniaev.bookstorage.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    
    private final BookRepository bookRepository;
    
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    
    @Override
    public boolean insert(String title, int year, int pageCount, int authorId, int genreId) {
        
        if (title == null || title.isBlank() || year <= 0 ||
                pageCount <= 0 || authorId <= 0 || genreId <= 0) {
            return false;
        }
        
        Book book = new Book();
        
        book.setTitle(title);
        book.setYear(year);
        book.setPageCount(pageCount);
        book.setAuthorId(authorId);
        book.setGenreId(genreId);
        
        bookRepository.save(book);
        
        return true;
    }
    
    @Override
    public Book getById(int id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }
    
    @Override
    public int update(int id, String title, int year, int pageCount, int authorId, int genreId) {
        
        Book book = bookRepository.findById(id);
        
        if (book == null) {
            return 0;
        } else {
            if (!title.isBlank()) {
                book.setTitle(title);
            }
            if (year > 0) {
                book.setYear(year);
            }
            if (pageCount > 0) {
                book.setPageCount(pageCount);
            }
            if (authorId > 0) {
                book.setAuthorId(authorId);
            }
            if (genreId > 0) {
                book.setGenreId(genreId);
            }
            
            bookRepository.update(book);
            
            return id;
        }
    }
    
    @Override
    public int deleteById(int id) {
        
        Book book = bookRepository.findById(id);
        
        if (book == null) {
            return 0;
        } else {
            bookRepository.delete(id);
            return id;
        }
    }

    @Override
    public void deleteAll() {
        bookRepository.deleteAll();
    }
}
