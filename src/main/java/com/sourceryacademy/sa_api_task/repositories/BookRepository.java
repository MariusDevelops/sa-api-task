package com.sourceryacademy.sa_api_task.repositories;

import com.sourceryacademy.sa_api_task.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByYear(int year);
    List<Book> findByRating(Double rating);
}