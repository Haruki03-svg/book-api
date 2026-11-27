package com.example.bookapi.controller;
import com.example.bookapi.model.Book;
import com.example.bookapi.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(BookController.class)
class BookControllerTest {
  @Autowired MockMvc mvc;
  @MockBean BookService service;
  @Test void versionEndpointWorks() throws Exception {
    mvc.perform(get("/api/version")).andExpect(status().isOk()).andExpect(jsonPath("$.app").value("book-api"));
  }
  @Test void listsBooks() throws Exception {
    when(service.findAll()).thenReturn(List.of(new Book(1L,"Dune","Frank Herbert")));
    mvc.perform(get("/api/books")).andExpect(status().isOk()).andExpect(jsonPath("$[0].title").value("Dune"));
  }
  @Test void createsBook() throws Exception {
    when(service.add(any())).thenReturn(new Book(2L,"1984","George Orwell"));
    mvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON)
      .content("{\"title\":\"1984\",\"author\":\"George Orwell\"}"))
      .andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(2));
  }
}
