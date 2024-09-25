package com.sourceryacademy.sa_api_task.controllers;

import com.sourceryacademy.sa_api_task.dtos.RatingRequestDTO;
import com.sourceryacademy.sa_api_task.models.Book;
import com.sourceryacademy.sa_api_task.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Double rating) {
        return bookService.filterBooks(title, author, year, rating);
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping("/{id}/rate")
    public ResponseEntity<Book> rateBook(@PathVariable Long id, @RequestBody RatingRequestDTO ratingRequestDTO) {
        Book updatedBook = bookService.rateBook(id, ratingRequestDTO.getRating());
        if (updatedBook != null) {
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}