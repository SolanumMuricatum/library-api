package com.pepino.bookstorageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO implements Serializable {
    String isbn;
    String name;
    String genre;
    String description;
    String author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDTO bookDTO = (BookDTO) o;
        return Objects.equals(isbn, bookDTO.isbn) && Objects.equals(name, bookDTO.name) && Objects.equals(genre, bookDTO.genre) && Objects.equals(description, bookDTO.description) && Objects.equals(author, bookDTO.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, name, genre, description, author);
    }
}

// добавить связь с трекером, добавив статус, дату взятия, дату возврата и того, кто взял