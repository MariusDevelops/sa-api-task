package com.sourceryacademy.sa_api_task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sourceryacademy.sa_api_task.controllers.BookController;
import com.sourceryacademy.sa_api_task.dtos.RatingRequestDTO;
import com.sourceryacademy.sa_api_task.models.Book;
import com.sourceryacademy.sa_api_task.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Book> mockBooks;

    @BeforeEach
    void setUp() {
        mockBooks = Arrays.asList(
                new Book(1L, "The Hobbit", "J.R.R. Tolkien", 1937, 5.0),
                new Book(2L, "1984", "George Orwell", 1949, 4.8)
        );
    }

    @Test
    void testGetAllBooks() throws Exception {
        when(bookService.filterBooks(null, null, null, null)).thenReturn(mockBooks);

        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("The Hobbit"))
                .andExpect(jsonPath("$[1].title").value("1984"));
    }

    @Test
    void testGetBookById() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(mockBooks.get(0));

        mockMvc.perform(get("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("The Hobbit"))
                .andExpect(jsonPath("$.author").value("J.R.R. Tolkien"));
    }

    @Test
    void testCreateBook() throws Exception {
        Book newBook = new Book(3L, "Dune", "Frank Herbert", 1965, 4.9);

        when(bookService.createBook(any(Book.class))).thenReturn(newBook);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Dune"))
                .andExpect(jsonPath("$.author").value("Frank Herbert"));
    }

    @Test
    void testRateBook() throws Exception {
        Book updatedBook = mockBooks.get(0);
        updatedBook.setRating(4.5);
        when(bookService.rateBook(1L, 4.5)).thenReturn(updatedBook);

        // Creating a RatingRequestDTO
        RatingRequestDTO ratingRequestDTO = new RatingRequestDTO();
        ratingRequestDTO.setRating(4.5);

        mockMvc.perform(put("/api/books/1/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ratingRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(4.5));
    }

    @Test
    void testRateNonExistentBook() throws Exception {
        when(bookService.rateBook(999L, 4.5)).thenReturn(null);

        RatingRequestDTO ratingRequestDTO = new RatingRequestDTO();
        ratingRequestDTO.setRating(4.5);

        mockMvc.perform(put("/api/books/999/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ratingRequestDTO)))
                .andExpect(status().isNotFound());
    }
}
