package com.eventbooking.bookingservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class) // <--- Enables auto-timestamps
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long eventId;

    private Long venueId; // Optional: good for analytics, saves an API call to Event Service

    @Column(nullable = false)
    private Integer ticketCount;

    @Column(nullable = false)
    private BigDecimal totalAmount; // <--- ALWAYS store the price paid!

    @Enumerated(EnumType.STRING) // <--- Stores "CONFIRMED" in DB instead of 0 or 1
    @Column(nullable = false)
    private BookingStatus status;

    @CreatedDate // <--- Auto-fills when created
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate // <--- Auto-updates when status changes
    private Instant updatedAt;
}