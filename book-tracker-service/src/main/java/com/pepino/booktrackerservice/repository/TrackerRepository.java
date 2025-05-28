package com.pepino.booktrackerservice.repository;

import com.pepino.booktrackerservice.entity.Tracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface TrackerRepository extends JpaRepository<Tracker, UUID> {
}
