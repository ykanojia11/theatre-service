package com.movies.service.impl;

import com.movies.dto.BookingDTO;
import com.movies.repository.BookingRepository;
import com.movies.repository.ShowRepository;
import com.movies.repository.entities.Booking;
import com.movies.repository.entities.Show;
import com.movies.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, ShowRepository showRepository) {
        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
    }

    @Override
    @Transactional
    public BookingDTO bookTickets(Long showId, String userId, List<String> seatNumbers) {
        Show show = showRepository.findById(showId).orElseThrow(() -> new RuntimeException("Show not found"));
        if (show.getAvailableSeats() < seatNumbers.size()) {
            throw new RuntimeException("Not enough seats available");
        }
        // Check for already booked seats
        List<Booking> existingBookings = bookingRepository.findAll();
        HashSet<String> bookedSeats = new HashSet<>();
        for (Booking b : existingBookings) {
            if (b.getShow().getId().equals(showId)) {
                bookedSeats.addAll(b.getSeatNumbers());
            }
        }
        for (String seat : seatNumbers) {
            if (bookedSeats.contains(seat)) {
                throw new RuntimeException("Seat " + seat + " is already booked for this show");
            }
        }
        // Discount strategy can be applied here
        double pricePerSeat = 200.0; // Example price
        double totalPrice = pricePerSeat * seatNumbers.size();
        double discount = DiscountStrategyFactory.getStrategy(show, seatNumbers).applyDiscount(totalPrice, seatNumbers.size());
        show.setAvailableSeats(show.getAvailableSeats() - seatNumbers.size());
        showRepository.save(show);
        Booking booking = Booking.builder()
                .show(show)
                .seatNumbers(seatNumbers)
                .userId(userId)
                .bookingTime(LocalDateTime.now())
                .totalPrice(totalPrice)
                .discountApplied(discount)
                .status("CONFIRMED")
                .build();

        final Booking savedBooking = bookingRepository.save(booking);

        return BookingDTO.builder()
                .id(savedBooking.getId())
                .showId(savedBooking.getShow().getId())
                .userId(savedBooking.getUserId())
                .seatNumbers(savedBooking.getSeatNumbers())
                .bookingTime(savedBooking.getBookingTime())
                .totalPrice(savedBooking.getTotalPrice())
                .discountApplied(savedBooking.getDiscountApplied())
                .status(savedBooking.getStatus()).build();
    }

    @Override
    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public List<BookingDTO> getBookingsByUser(String userId) {
        return bookingRepository.findAll().stream()
                .filter(b -> b.getUserId().equals(userId))
                .map(b -> BookingDTO.builder()
                .id(b.getId())
                .showId(b.getShow().getId())
                .userId(b.getUserId())
                        .seatNumbers(b.getSeatNumbers())
                        .bookingTime(b.getBookingTime())
                        .totalPrice(b.getTotalPrice())
                        .discountApplied(b.getDiscountApplied())
                        .status(b.getStatus()).build()).collect(java.util.stream.Collectors.toList());
    }
}
