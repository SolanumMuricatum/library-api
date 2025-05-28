package com.pepino.bookstorageservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "book-tracker-service")
public interface TrackerServiceClient {
    @DeleteMapping("/tracker/delete/{id}")
    ResponseEntity<?> deleteTrackerSoft(@PathVariable(value = "id") UUID id);
}
