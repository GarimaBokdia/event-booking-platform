package com.eventbooking.venueservice.repository;

import com.eventbooking.venueservice.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}
