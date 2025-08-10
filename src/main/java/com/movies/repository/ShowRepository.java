package com.movies.repository;

import com.movies.repository.entities.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findByMovie_IdAndTheatre_CityAndShowTimeBetween(Long movieId, String city, LocalDateTime start, LocalDateTime end);
}
