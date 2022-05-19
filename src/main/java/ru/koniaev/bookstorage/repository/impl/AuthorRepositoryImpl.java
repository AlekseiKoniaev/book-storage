package ru.koniaev.bookstorage.repository.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.koniaev.bookstorage.mapper.AuthorRowMapper;
import ru.koniaev.bookstorage.model.Author;
import ru.koniaev.bookstorage.repository.AuthorRepository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    
    private final RowMapper<Author> rowMapper;
    
    private final JdbcTemplate jdbcTemplate;
    
    public AuthorRepositoryImpl(AuthorRowMapper rowMapper, DataSource dataSource) {
        this.rowMapper = rowMapper;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    
    @Override
    public void save(Author author) {
        String sql = "insert into author(first_name, second_name, birthday) values (?,?,?)";
        jdbcTemplate.update(sql, author.getFirstName(), author.getSecondName(),
                author.getBirthday());
    }
    
    @Override
    public Author findById(int id) {
        
        try {
            String sql = "select * from author where id = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (DataAccessException e) {
            return null;
        }
    }
    
    @Override
    public List<Author> findAll() {
       
        try {
            return jdbcTemplate.query("select * from author", rowMapper);
        } catch (DataAccessException e) {
            return new ArrayList<>();
        }
    }
    
    @Override
    public void update(Author author) {
        String sql = "update author set (first_name, second_name, birthday) = (?,?,?) " +
                "where id = ?";
        jdbcTemplate.update(sql, author.getFirstName(), author.getSecondName(),
                author.getBirthday(), author.getId());
    }
    
    @Override
    public void delete(int id) {
        jdbcTemplate.update("delete from author where id = ?", id);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from author");
    }
}
