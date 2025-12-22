package com.eventbooking.eventservice.repository;
//
//import com.eventbooking.eventservice.entity.Event;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import java.util.List;
//
//public interface EventRepository extends JpaRepository<Event, Long> {
//    List<Event> findByOrganizerId(Long organizerId);
//    List<Event> findByIsActiveTrue();
//
//    // 1. Standard findAll with pagination
//    Page<Event> findAll(Pageable pageable);
//    Page<Event> findByCategory(String category, Pageable pageable);
//}
//

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.eventbooking.eventservice.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

    // 1. Fetch all events for a specific organizer (Paged)
    Page<Event> findByOrganizerId(Long organizerId);

    // 2. Fetch all active events (Paged)
    Page<Event> findByIsActiveTrue();

    // 3. (Optional) Explicit findAll is provided by JpaRepository,
    // but you can declare it if you want to be specific
    //Page<Event> findAll(Pageable pageable);
}