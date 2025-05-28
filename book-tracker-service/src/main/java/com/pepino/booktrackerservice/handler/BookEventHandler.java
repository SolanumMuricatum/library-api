package com.pepino.booktrackerservice.handler;

import com.pepino.booktrackerservice.repository.TrackerRepository;
import com.pepino.booktrackerservice.service.TrackerService;
import com.pepino.event.BookCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "book-created-events-topic")
public class BookEventHandler { // rewrite just for BookEventHandler
    //TODO add methods for other events
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final TrackerService trackerService;

    @Autowired
    public BookEventHandler(TrackerService trackerService){
        this.trackerService = trackerService;
    }

    @KafkaHandler
    public void handle(BookCreatedEvent bookCreatedEvent){
        LOGGER.info("Received event: {}", bookCreatedEvent.getName());
        trackerService.saveTracker(bookCreatedEvent.getId());
    }
}
