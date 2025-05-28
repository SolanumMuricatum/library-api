package com.pepino.bookstorageservice.controller;

import com.pepino.bookstorageservice.client.TrackerServiceClient;
import com.pepino.bookstorageservice.dto.BookDTO;
import com.pepino.bookstorageservice.entity.Book;
import com.pepino.exception.BookException;
import com.pepino.bookstorageservice.service.BookService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/getById/{id}") //а зачем... это небезопасно
    public ResponseEntity<?> getBookById(@PathVariable(value = "id") UUID id){
        try {
            BookDTO bookDTO = bookService.getBookById(id);
            return ResponseEntity.ok(bookDTO);
        } catch (BookException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getByIsbn/{isbn}")
    public ResponseEntity<?> getBookByISBN(@PathVariable(value = "isbn") String isbn){
        try{
            BookDTO bookDTO = bookService.getBookByISBN(isbn);
            return ResponseEntity.ok(bookDTO);
        } catch (BookException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllBooks(){
        try{
            List<BookDTO> booksDTO = bookService.getAllBooks();
            return ResponseEntity.ok(booksDTO);
        } catch (BookException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveBook(@RequestBody BookDTO bookDTO){
        try {
            bookService.saveBook(bookDTO);
            return ResponseEntity.status(201).body(bookDTO);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    //переписать под putMapping, если это будет необходимо
    @PatchMapping("/update/{isbn}")
    public ResponseEntity<?> updateBook(@RequestBody BookDTO bookDTO, @PathVariable(value = "isbn") String currentIsbn){
        try {
            bookService.updateBook(bookDTO, currentIsbn);
            return ResponseEntity.ok().build();
        } catch (BookException e) {
            return ResponseEntity.status(409).build();
        }
    }

    @DeleteMapping("/delete/{isbn}") //переделать удаление под isbn
    public ResponseEntity<?> deleteBookSoft(@PathVariable(value = "isbn") String currentIsbn){
        try {
            bookService.deleteBookSoft(currentIsbn);
            return ResponseEntity.ok().build();
        } catch (BookException e) {
            return ResponseEntity.status(404).build();
        }
    }

}
