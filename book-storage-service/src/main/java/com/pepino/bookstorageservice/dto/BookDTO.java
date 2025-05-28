package com.pepino.bookstorageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO implements Serializable {
    String isbn;
    String name;
    String genre;
    String description;
    String author;
}

// добавить связь с трекером, добавив статус, дату взятия, дату возврата и того, кто взял