package ru.koniaev.bookstorage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.koniaev.bookstorage.controller.GenreController;
import ru.koniaev.bookstorage.model.Genre;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GenreMvcTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private GenreController controller;
    
    @Test
    void insert_shouldReturnOk() throws Exception {
        Genre genre = createGenre();
        
        this.mockMvc.perform(post("/api/genre/")
                .param("name", genre.getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{result: true}"));
    }
    
    private Genre createGenre() {
        Genre genre = new Genre();
        genre.setName("Horror");
        
        return genre;
    }
}
