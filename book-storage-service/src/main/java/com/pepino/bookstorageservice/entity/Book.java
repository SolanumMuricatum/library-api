package com.pepino.bookstorageservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    @Column(name = "isbn", unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]{13}$", message = "ISBN must be exactly 13 digits")
    private String isbn;
    @Column(name = "name", length = 225, nullable = false)
    private String name;
    @Column(name = "genre", length = 100, nullable = false)
    private String genre;
    @Column(name = "description", length = 1000, nullable = false)
    private String description;
    @Column(name = "author", length = 225, nullable = false)
    private String author;
    @Column(name = "deleted_at", nullable = false)
    private boolean deletedAt;
}
