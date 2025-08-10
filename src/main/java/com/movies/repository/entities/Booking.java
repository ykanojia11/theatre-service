package com.movies.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @ElementCollection
    @CollectionTable(name = "booking_seats", joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "seat_number")
    private List<String> seatNumbers;

    private String userId;
    private String role; // PARTNER or CUSTOMER
    private String partnerId; // For partner bookings
    private Double commission; // For partner bookings
    private String paymentMethod; // For customer bookings
    private LocalDateTime bookingTime;
    private double totalPrice;
    private double discountApplied;
    private String status; // e.g., CONFIRMED, CANCELLED
}
