package ru.koniaev.bookstorage.repository.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.koniaev.bookstorage.mapper.AuthorRowMapper;
import ru.koniaev.bookstorage.model.Author;
import ru.koniaev.bookstorage.repository.EntityRepository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AuthorRepositoryImpl implements EntityRepository<Integer, Author> {
    
    private final RowMapper<Author> rowMapper;
    
    private final JdbcTemplate jdbcTemplate;
    
    public AuthorRepositoryImpl(AuthorRowMapper rowMapper, DataSource dataSource) {
        this.rowMapper = rowMapper;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    
    @Override
    public boolean save(Author author) {
        
        try {
            String sql = "insert into author(first_name, second_name, birthday) values (?,?,?)";
            jdbcTemplate.update(sql, author.getFirstName(), author.getSecondName(),
                    author.getBirthday());
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
    
    @Override
    public Author findById(Integer id) {
        
        try {
            String sql = "select * from author where id = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (DataAccessException e) {
            return null;
        }
    }
    
    @Override
    public List<Author> findAll() {
        return jdbcTemplate.query("select * from author", rowMapper);
    }
    
    @Override
    public boolean update(Author author) {
        
        try {
            String sql = "update author set (first_name, second_name, birthday) = (?,?,?) " +
                    "where id = ?";
            jdbcTemplate.update(sql, author.getFirstName(), author.getSecondName(),
                    author.getBirthday(), author.getId());
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
    
    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("delete from author where id = ?", id);
    }
    
    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from author");
    }
}
