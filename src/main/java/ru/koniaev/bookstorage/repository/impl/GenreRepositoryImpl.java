package ru.koniaev.bookstorage.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.koniaev.bookstorage.model.Genre;
import ru.koniaev.bookstorage.repository.GenreRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryImpl implements GenreRepository {
    
    private final RowMapper<Genre> rowMapper = (rs, rowNum) -> {
        Genre genre = new Genre();
        genre.setId(rs.getInt("id"));
        genre.setName(rs.getString("name"));
        return genre;
    };
    
    private final JdbcTemplate jdbcTemplate;
    
    public GenreRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    
    @Override
    public void save(Genre genre) {
        String sql = "insert into genre(name) values (?)";
        jdbcTemplate.update(sql, genre.getName());
    }
    
    @Override
    public Optional<Genre> findById(int id) {
        try {
            String sql = "select * from genre where id = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
    
    @Override
    public Optional<List<Genre>> findAll() {
        try {
            return Optional.of(jdbcTemplate.query("select * from genre", rowMapper));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
    
    @Override
    public void update(Genre genre) {
        String sql = "update genre set name = ? where id = ?";
        jdbcTemplate.update(sql, genre.getName(), genre.getId());
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
