package com.pepino.booktrackerservice.service;

import com.pepino.bookstorageservice.dto.BookDTO;
import com.pepino.bookstorageservice.entity.Book;
import com.pepino.exception.BookException;
import com.pepino.booktrackerservice.entity.Tracker;
import com.pepino.booktrackerservice.repository.TrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrackerServiceImpl implements TrackerService{

    private final TrackerRepository trackerRepository;

    @Autowired
    public TrackerServiceImpl(TrackerRepository trackerRepository){
        this.trackerRepository = trackerRepository;
    }
    @Override
    public void saveTracker(UUID bookId) {
        Tracker tracker = new Tracker(bookId);
        trackerRepository.save(tracker);
    }

    @Override
    public List<BookDTO> getFreeBooks() {
        return null;
    }

    @Override
    public void changeBookStatus(UUID bookId, String status) {

    }

    @Override
    public void deleteTrackerSoft(UUID bookId) throws BookException{
        Optional<Tracker> optionalTracker = trackerRepository.findById(bookId);

        if(optionalTracker.isPresent()){
            Tracker tracker = optionalTracker.get();
            tracker.setDeletedAt(true);
            trackerRepository.save(tracker);
        }
        else{
            throw new BookException("Book for deletion doesn't exist");
        }
    }
}
