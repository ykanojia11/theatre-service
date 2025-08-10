package com.movies.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowDTO {
    private Long id;
    private Long theatreId;
    private Long movieId;
    private String theatreName;
    private String movieTitle;
    private LocalDateTime showTime;
    private int availableSeats;
}
