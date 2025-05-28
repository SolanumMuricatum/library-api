package com.pepino.bookstorageservice.service;

import com.pepino.bookstorageservice.dto.BookDTO;
import com.pepino.bookstorageservice.entity.Book;
import com.pepino.exception.BookException;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface BookService {
    public void saveBook(BookDTO bookDTO) throws BookException, ExecutionException, InterruptedException;
    public List<BookDTO> getAllBooks() throws BookException;
    public BookDTO getBookById(UUID id) throws BookException;
    public BookDTO getBookByISBN(String ISBN) throws BookException;
    public void updateBook(BookDTO bookDTO, String currentIsbn) throws BookException;
    public void deleteBookSoft(String isbn) throws BookException;
}
