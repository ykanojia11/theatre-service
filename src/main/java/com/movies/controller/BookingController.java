package com.movies.controller;

import com.movies.dto.BookingDTO;
import com.movies.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@Validated
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public BookingDTO bookTickets(@RequestBody @Validated BookingDTO dto) {
        try {
            return bookingService.bookTickets(dto.getShowId(), dto.getUserId(), dto.getSeatNumbers());
        } catch (RuntimeException ex) {
            String msg = ex.getMessage();
            if (msg != null) {
                if (msg.contains("already booked")) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
                } else if (msg.contains("Not enough seats available")) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
                } else if (msg.contains("Show not found")) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg);
                }
            }
            throw ex;
        }
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/{id}")
    public void cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/user/{userId}")
    public List<BookingDTO> getBookingsByUser(@PathVariable String userId) {
        return bookingService.getBookingsByUser(userId);
    }
}
