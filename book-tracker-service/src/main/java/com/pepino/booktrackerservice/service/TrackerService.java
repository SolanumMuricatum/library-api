package com.pepino.booktrackerservice.service;

import com.pepino.bookstorageservice.dto.BookDTO;
import com.pepino.bookstorageservice.entity.Book;
import com.pepino.exception.BookException;

import java.util.List;
import java.util.UUID;

public interface TrackerService {
    public void saveTracker(UUID bookId);
    public List<BookDTO> getFreeBooks();
    public void changeBookStatus(UUID bookId, String status);
    public void deleteTrackerSoft(UUID bookId) throws BookException;
}
