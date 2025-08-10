package com.movies.service;

import com.movies.dto.BookingDTO;
import com.movies.repository.entities.Booking;
import java.util.List;

public interface BookingService {
    BookingDTO bookTickets(Long showId, String userId, List<String> seatNumbers);
    void cancelBooking(Long bookingId);
    List<BookingDTO> getBookingsByUser(String userId);
    // Other methods: getBookingHistory, etc.
}
