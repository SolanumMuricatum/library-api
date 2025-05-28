package com.pepino.bookstorageservice.repository;

import com.pepino.bookstorageservice.dto.BookDTO;
import com.pepino.bookstorageservice.entity.Book;
import com.pepino.exception.BookException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    @Query("SELECT new Book(b.id, b.isbn, b.name, b.genre, b.description, b.author, b.deletedAt) " +
            "FROM Book b " +
            "WHERE b.deletedAt = false")
    public List<Book> getAllBooks();

    @Query("SELECT new Book(b.id, b.isbn, b.name, b.genre, b.description, b.author, b.deletedAt) " +
            "FROM Book b " +
            "WHERE b.id = :id AND b.deletedAt = false ")
    public Optional<Book> getBookById(@Param("id") UUID id);

    @Query("SELECT new Book(b.id, b.isbn, b.name, b.genre, b.description, b.author, b.deletedAt) " +
            "FROM Book b " +
            "WHERE b.isbn = :isbn AND b.deletedAt = false ")
    public Optional<Book> getBookByISBN(@Param("isbn") String isbn);


}
