package com.sourceryacademy.sa_api_task;

import com.sourceryacademy.sa_api_task.models.Book;
import com.sourceryacademy.sa_api_task.repositories.BookRepository;
import com.sourceryacademy.sa_api_task.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        book1 = new Book(1L, "The Hobbit", "J.R.R. Tolkien", 1937, 5.0);
        book2 = new Book(2L, "1984", "George Orwell", 1949, 4.8);
    }

    @Test
    void getAllBooks_shouldReturnAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAllBooks();

        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals(book1.getTitle(), books.get(0).getTitle());
    }

    @Test
    void filterBooks_shouldReturnBooksByTitle() {
        when(bookRepository.findByTitleContainingIgnoreCase("The Hobbit")).thenReturn(Arrays.asList(book1));

        List<Book> books = bookService.filterBooks("The Hobbit", null, null, null);

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("The Hobbit", books.get(0).getTitle());
    }

    @Test
    void filterBooks_shouldReturnBooksByAuthor() {
        when(bookRepository.findByAuthorContainingIgnoreCase("George Orwell")).thenReturn(Arrays.asList(book2));

        List<Book> books = bookService.filterBooks(null, "George Orwell", null, null);

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("George Orwell", books.get(0).getAuthor());
    }

    @Test
    void getBookById_shouldReturnBookWhenFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        Book foundBook = bookService.getBookById(1L);

        assertNotNull(foundBook);
        assertEquals(book1.getTitle(), foundBook.getTitle());
    }

    @Test
    void getBookById_shouldReturnNullWhenNotFound() {
        when(bookRepository.findById(3L)).thenReturn(Optional.empty());

        Book foundBook = bookService.getBookById(3L);

        assertNull(foundBook);
    }

    @Test
    void rateBook_shouldUpdateRating() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        when(bookRepository.save(any(Book.class))).thenReturn(book1);

        Book ratedBook = bookService.rateBook(1L, 4.5);

        assertNotNull(ratedBook);
        assertEquals(4.5, ratedBook.getRating());
        verify(bookRepository, times(1)).save(book1);
    }

    @Test
    void rateBook_shouldReturnNullWhenBookNotFound() {
        when(bookRepository.findById(3L)).thenReturn(Optional.empty());

        Book ratedBook = bookService.rateBook(3L, 4.5);

        assertNull(ratedBook);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void createBook_shouldSaveNewBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book1);

        Book createdBook = bookService.createBook(book1);

        assertNotNull(createdBook);
        assertEquals(book1.getTitle(), createdBook.getTitle());
        verify(bookRepository, times(1)).save(book1);
    }
}
