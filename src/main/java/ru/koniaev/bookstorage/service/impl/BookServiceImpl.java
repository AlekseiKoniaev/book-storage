package ru.koniaev.bookstorage.service.impl;

import org.springframework.stereotype.Service;
import ru.koniaev.bookstorage.model.Book;
import ru.koniaev.bookstorage.repository.EntityRepository;
import ru.koniaev.bookstorage.service.EntityService;

import java.util.List;

@Service
public class BookServiceImpl implements EntityService<Book, Integer> {
    
    private final EntityRepository<Book, Integer> bookRepository;
    
    public BookServiceImpl(EntityRepository<Book, Integer> bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    
    @Override
    public boolean insert(Object...args) {
    
        String title;
        Integer year;
        Integer pageCount;
        Integer authorId;
        Integer genreId;
        try {
            title = (String) args[0];
            year = (Integer) args[1];
            pageCount = (Integer) args[2];
            authorId = (Integer) args[3];
            genreId = (Integer) args[4];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
        
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
    public Book getById(Integer id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }
    
    @Override
    public int update(Integer id, Object...args) {
    
        String title;
        Integer year;
        Integer pageCount;
        Integer authorId;
        Integer genreId;
        try {
            title = (String) args[0];
            year = (Integer) args[1];
            pageCount = (Integer) args[2];
            authorId = (Integer) args[3];
            genreId = (Integer) args[4];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException e) {
            return 0;
        }
        
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
    public int deleteById(Integer id) {
        
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
