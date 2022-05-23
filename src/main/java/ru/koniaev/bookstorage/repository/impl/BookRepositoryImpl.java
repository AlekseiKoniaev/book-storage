package ru.koniaev.bookstorage.repository.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.koniaev.bookstorage.mapper.BookRowMapper;
import ru.koniaev.bookstorage.model.Book;
import ru.koniaev.bookstorage.repository.BookRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {
    
    private final RowMapper<Book> rowMapper;
    
    private final JdbcTemplate jdbcTemplate;
    
    public BookRepositoryImpl(BookRowMapper rowMapper, DataSource dataSource) {
        this.rowMapper = rowMapper;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    
    @Override
    public boolean save(Book book) {
        try {
            String sql = "insert into book(title, year, page_count, author_id, genre_id) values (?,?,?,?,?)";
            jdbcTemplate.update(sql, book.getTitle(), book.getYear(), book.getPageCount(),
                    book.getAuthorId(), book.getGenreId());
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
    
    @Override
    public Book findById(int id) {
        try {
            return jdbcTemplate.queryForObject("select * from book where id = ?", rowMapper, id);
        } catch (DataAccessException e) {
            return null;
        }
    }
    
    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query("select * from book", rowMapper);
    }
    
    @Override
    public boolean update(Book book) {
        try {
            String sql = "update book set (title, year, page_count, author_id, genre_id) = (?,?,?,?,?) " +
                    "where id = ?";
            jdbcTemplate.update(sql, book.getTitle(), book.getYear(), book.getPageCount(),
                    book.getAuthorId(), book.getGenreId(), book.getId());
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
    
    @Override
    public void delete(int id) {
        jdbcTemplate.update("delete from book where id = ?", id);
    }
    
    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from book");
    }
}
