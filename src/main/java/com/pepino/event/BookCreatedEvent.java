package com.pepino.event;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

public class BookCreatedEvent extends AbstractBookEvent {
    public BookCreatedEvent(UUID id, String isbn, String name, String genre, String description, String author, boolean deletedAt) {
        super(id, isbn, name, genre, description, author, deletedAt);
    }

    public BookCreatedEvent() {
    }
}
