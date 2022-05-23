package ru.koniaev.bookstorage.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.koniaev.bookstorage.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookRowMapper implements RowMapper<Book> {
    
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setYear(rs.getInt("year"));
        book.setPageCount(rs.getInt("page_count"));
        book.setAuthorId(rs.getInt("author_id"));
        book.setGenreId(rs.getInt("genre_id"));
        return book;
    }
}
