package ru.koniaev.bookstorage.repository.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.koniaev.bookstorage.mapper.GenreRowMapper;
import ru.koniaev.bookstorage.model.Genre;
import ru.koniaev.bookstorage.repository.GenreRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryImpl implements GenreRepository {
    
    private final RowMapper<Genre> rowMapper;
    
    private final JdbcTemplate jdbcTemplate;
    
    public GenreRepositoryImpl(GenreRowMapper rowMapper, DataSource dataSource) {
        this.rowMapper = rowMapper;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    
    @Override
    public boolean save(Genre genre) {
        try {
            String sql = "insert into genre(name) values (?)";
            jdbcTemplate.update(sql, genre.getName());
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
    
    @Override
    public Genre findById(int id) {
        try {
            String sql = "select * from genre where id = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (DataAccessException e) {
            return null;
        }
    }
    
    @Override
    public List<Genre> findAll() {
        return jdbcTemplate.query("select * from genre", rowMapper);
    }
    
    @Override
    public boolean update(Genre genre) {
        try {
            String sql = "update genre set name = ? where id = ?";
            jdbcTemplate.update(sql, genre.getName(), genre.getId());
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
    
    @Override
    public void delete(int id) {
        jdbcTemplate.update("delete from genre where id = ?", id);
    }
    
    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from genre");
    }
}
