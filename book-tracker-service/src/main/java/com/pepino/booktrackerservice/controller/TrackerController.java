package com.pepino.booktrackerservice.controller;

import com.pepino.bookstorageservice.service.BookService;
import com.pepino.booktrackerservice.service.TrackerService;
import com.pepino.exception.BookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/tracker")
public class TrackerController {
    private final TrackerService trackerService;

    @Autowired
    public TrackerController(TrackerService trackerService){
        this.trackerService = trackerService;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTrackerSoft(@PathVariable(value = "id") UUID id){
        try {
            trackerService.deleteTrackerSoft(id);
            return ResponseEntity.ok().build();
        } catch (BookException e) {
            return ResponseEntity.status(404).build();
        }
    }
}
