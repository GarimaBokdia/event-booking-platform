package com.eventbooking.bookingservice.repository;

import com.eventbooking.bookingservice.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Spring generates the pagination query automatically!
    Page<Booking> findByUserId(Long userId, Pageable pageable);

    // If you have a generic findAll, it's already there by default:
    // Page<Booking> findAll(Pageable pageable);
}