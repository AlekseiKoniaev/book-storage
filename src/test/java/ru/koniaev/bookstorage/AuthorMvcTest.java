package ru.koniaev.bookstorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.koniaev.bookstorage.controller.AuthorController;
import ru.koniaev.bookstorage.model.Author;
import ru.koniaev.bookstorage.service.impl.AuthorServiceImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest
@MockBean(AuthorServiceImpl.class)
//@AutoConfigureMockMvc
//@TestPropertySource("/application-test.properties")
//@ActiveProfiles("test")
@Sql(value = {"/sql/author/author-before.sql", "/sql/author/create-author-before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AuthorMvcTest {
    
    @Autowired
    private MockMvc mockMvc;
//
//    @Autowired
//    private AuthorController controller;
    
    @Test
    void insert_shouldReturnOk_whenParamsCorrectAndNotExists() throws Exception {
        Author author = createAuthors().get(2);
        
        this.mockMvc.perform(post("/api/author/")
                        .param("firstName", author.getFirstName())
                        .param("secondName", author.getSecondName())
                        .param("birthday", author.getBirthday().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{result:true}"));
    }
    
    @Test
    void insert_shouldReturnOk_whenExists() throws Exception {
        Author author = createAuthors().get(0);
        
        this.mockMvc.perform(post("/api/author/")
                        .param("firstName", author.getFirstName())
                        .param("secondName", author.getSecondName())
                        .param("birthday", author.getBirthday().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{result:true}"));
    }
    
    @Test
    void insert_shouldReturnBadRequest_whenParamsNotCorrect() throws Exception {
        Author author = createAuthors().get(2);
        
        this.mockMvc.perform(post("/api/author/")
                        .param("firstName", author.getFirstName())
                        .param("birthday", author.getBirthday().toString()))
                .andDo(print())
                .andExpect(status().is(400));
    }
    
    
    @Test
    void get_shouldReturnAuthor() throws Exception {
        Author author = createAuthors().get(0);
        JSONObject jsonObject = getAuthorJsonObject(author);
    
        this.mockMvc.perform(get("/api/author/{id}", author.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonObject.toString()));
    }
    
    
    @Test
    void list_shouldReturnAuthorList() throws Exception {
        List<Author> authors = createAuthors();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i <= 1; i++) {
            Author author = authors.get(i);
            JSONObject jsonObject = getAuthorJsonObject(author);
            jsonArray.put(jsonObject);
        }
    
        this.mockMvc.perform(get("/api/author/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonArray.toString()));
    }
    
    
    @Test
    void update_shouldReturnId() throws Exception {
        Author author = createAuthors().get(2);
        author.setId(1);
        
        this.mockMvc.perform(put("/api/author/{id}", author.getId())
                        .param("firstName", author.getFirstName())
                        .param("secondName", author.getSecondName())
                        .param("birthday", author.getBirthday().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{result:true,id:" + author.getId() + "}"));
    }
    
    
    @Test
    void delete_shouldReturnId() throws Exception {
        final int id = 1;
        
        this.mockMvc.perform(delete("/api/author/{id}", id))
                .andDo(print())
                .andExpect(status().is(204))
                .andExpect(content().json("{result:true,id:" + id + "}"));
    }
    
    
    @Test
    void deleteAll_shouldReturnOk() throws Exception {
        this.mockMvc.perform(delete("/api/author/"))
                .andDo(print())
                .andExpect(status().is(204))
                .andExpect(content().json("{result:true}"));
    }
    
    
    private List<Author> createAuthors() {
        List<Author> list = new ArrayList<>();
        
        Author author1 = new Author();
        Author author2 = new Author();
        Author author3 = new Author();
        
        author1.setId(1);
        author1.setFirstName("Alex");
        author1.setSecondName("Black");
        author1.setBirthday(Date.valueOf("1987-04-13"));
        list.add(author1);
        
        author2.setId(2);
        author2.setFirstName("Mike");
        author2.setSecondName("Green");
        author2.setBirthday(Date.valueOf("1974-01-02"));
        list.add(author2);
        
        author3.setId(3);
        author3.setFirstName("Jess");
        author3.setSecondName("Lowsen");
        author3.setBirthday(Date.valueOf("1987-10-24"));
        list.add(author3);
        
        return list;
    }
    
    private JSONObject getResponseJsonObject(boolean result) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return jsonObject;
    }
    
    private JSONObject getAuthorJsonObject(Author author) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", author.getId());
        jsonObject.put("firstName", author.getFirstName());
        jsonObject.put("secondName", author.getSecondName());
        jsonObject.put("birthday", author.getBirthday().toString());
        return jsonObject;
    }

}
