package com.sourceryacademy.sa_api_task.services;

import com.sourceryacademy.sa_api_task.models.Book;
import com.sourceryacademy.sa_api_task.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> filterBooks(String title, String author, Integer year, Double rating) {
        if (title != null) {
            return bookRepository.findByTitleContainingIgnoreCase(title);
        } else if (author != null) {
            return bookRepository.findByAuthorContainingIgnoreCase(author);
        } else if (year != null) {
            return bookRepository.findByYear(year);
        } else if (rating != null) {
            return bookRepository.findByRating(rating);
        }
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book rateBook(Long id, double newRating) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setRating(newRating);
            return bookRepository.save(book);
        }
        return null;
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }
}