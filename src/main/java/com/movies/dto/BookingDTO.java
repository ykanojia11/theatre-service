package com.movies.dto;

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
public class BookingDTO {
    private Long id;
    private Long showId;
    private String userId;
    private List<String> seatNumbers;
    private LocalDateTime bookingTime;
    private double totalPrice;
    private double discountApplied;
    private String status;
}
