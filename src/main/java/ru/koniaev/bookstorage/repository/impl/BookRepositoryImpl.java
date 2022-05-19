package ru.koniaev.bookstorage.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.koniaev.bookstorage.model.Book;
import ru.koniaev.bookstorage.repository.BookRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {
    
    private final RowMapper<Book> rowMapper = (rs, rowNum) -> {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setYear(rs.getInt("year"));
        book.setPageCount(rs.getInt("page_count"));
        book.setAuthorId(rs.getInt("author_id"));
        book.setGenreId(rs.getInt("genre_id"));
        return book;
    };
    
    private final JdbcTemplate jdbcTemplate;
    
    public BookRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    
    @Override
    public void save(Book book) {
        String sql = "insert into book(title, year, page_count, author_id, genre_id) values (?,?,?,?,?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getYear(), book.getPageCount(),
                book.getAuthorId(), book.getGenreId());
    }
    
    @Override
    public Optional<Book> findById(int id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from book where id = ?",
                    rowMapper, id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
    
    @Override
    public Optional<List<Book>> findAll() {
        try {
            return Optional.of(jdbcTemplate.query("select * from book", rowMapper));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
    
    @Override
    public void update(Book book) {
        String sql = "update book set (title, year, page_count, author_id, genre_id) = (?,?,?,?,?) " +
                "where id = ?";
        jdbcTemplate.update(sql, book.getTitle(), book.getYear(), book.getPageCount(),
                book.getAuthorId(), book.getGenreId(), book.getId());
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
