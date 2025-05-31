package com.pepino.bookstorageservice.service;

import com.pepino.bookstorageservice.client.TrackerServiceClient;
import com.pepino.bookstorageservice.dto.BookDTO;
import com.pepino.bookstorageservice.entity.Book;
import com.pepino.bookstorageservice.repository.BookRepository;
import com.pepino.event.BookCreatedEvent;
import com.pepino.exception.BookException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//можно и просто @Mock, будет быстрее
@SpringBootTest
public class BookServiceImplTest {
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private ModelMapper modelMapper;
   /* private final KafkaTemplate<UUID, BookCreatedEvent> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final TrackerServiceClient trackerServiceClient;*/
    @Autowired
    private BookServiceImpl bookServiceImpl;

    @Test
    void getBookByIdTest() throws BookException {
        UUID id = UUID.fromString("c97168e2-6d17-40eb-8e51-6acb00873eb1");
        Book book = new Book(id, "1234567891235", "kvas", "no", "no", "pepino", false);
        BookDTO bookDTO = new BookDTO("1234567891235", "kvas", "no", "no", "pepino");

        when(bookRepository.getBookById(id)).thenReturn(Optional.of(book));
        when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);

        BookDTO actualBook = bookServiceImpl.getBookById(id);

        assertEquals(bookDTO, actualBook);
    }

    @Test
    void getBookByIdWhenBookNotFoundThrowsException() {
        UUID id = UUID.randomUUID();

        when(bookRepository.getBookById(id)).thenReturn(Optional.empty());

        assertThrows(BookException.class, () -> bookServiceImpl.getBookById(id));
    }

    @Test
    void getBookByIsbnTest() throws BookException {
        UUID id = UUID.fromString("c97168e2-6d17-40eb-8e51-6acb00873eb1");
        String isbn = "1234567891235";
        Book book = new Book(id, isbn, "kvas", "no", "no", "pepino", false);
        BookDTO bookDTO = new BookDTO(isbn, "kvas", "no", "no", "pepino");

        when(bookRepository.getBookByISBN(isbn)).thenReturn(Optional.of(book));
        when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);

        BookDTO actualBook = bookServiceImpl.getBookByISBN(isbn);

        assertEquals(bookDTO, actualBook);
    }

    @Test
    void getBookByIsbnWhenBookNotFoundThrowsException() {
        String isbn = "1234567891235";

        when(bookRepository.getBookByISBN(isbn)).thenReturn(Optional.empty());

        assertThrows(BookException.class, () -> bookServiceImpl.getBookByISBN(isbn));
    }

    @Test
    void getAllBooksTest() throws BookException {

        UUID id = UUID.fromString("c97168e2-6d17-40eb-8e51-6acb00873eb1");
        String isbn = "1234567891235";

        List<Book> books = List.of(new Book(id, isbn, "kvas", "no", "no", "pepino", false));
        List<BookDTO> expectedBooksDTO = List.of(new BookDTO(isbn, "kvas", "no", "no", "pepino"));

        when(bookRepository.getAllBooks()).thenReturn(books);
        when(modelMapper.map(books.get(0), BookDTO.class)).thenReturn(expectedBooksDTO.get(0));

        List<BookDTO> actualBooks = bookServiceImpl.getAllBooks();

        assertEquals(expectedBooksDTO, actualBooks);
        verify(bookRepository).getAllBooks();
    }

    @Test
    void getAllBooksWhenBooksNotFoundThrowsException() {

        when(bookRepository.getAllBooks()).thenReturn(List.of());

        assertThrows(BookException.class, () -> bookServiceImpl.getAllBooks());
    }

    @Test
    void updateBookWhenBookNotFoundThrowsException() {
        String isbn = "1234567891235";
        BookDTO bookDTO = new BookDTO(isbn, "kvas", "no", "no", "pepino");

        when(bookRepository.getBookByISBN(isbn)).thenReturn(Optional.empty());

        assertThrows(BookException.class, () -> bookServiceImpl.updateBook(bookDTO ,isbn));
    }

    @Test
    void deleteBookSoftWhenBookNotFoundThrowsException() {
        String isbn = "1234567891235";

        when(bookRepository.getBookByISBN(isbn)).thenReturn(Optional.empty());

        assertThrows(BookException.class, () -> bookServiceImpl.deleteBookSoft(isbn));
    }

}
