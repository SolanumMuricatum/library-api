package com.pepino.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractBookEvent {
    private UUID id;
    String isbn;
    String name;
    String genre;
    String description;
    String author;
    boolean deletedAt;
}
