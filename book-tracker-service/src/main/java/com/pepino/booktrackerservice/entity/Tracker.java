package com.pepino.booktrackerservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "tracker")
public class Tracker {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "status", length = 100)
    private String status;
    @Column(name = "booking_start_date")
    private LocalDate bookingStartDate;
    @Column(name = "booking_end_date")
    private LocalDate bookingEndDate;
    @Column(name = "reader", length = 225)
    private String reader;
    @Column(name="deleted_at", nullable = false)
    private boolean deletedAt;

    public Tracker(UUID id) {
        this.id = id;
    }
}
