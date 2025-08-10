package com.movies.service.impl;

import com.movies.dto.ShowDTO;
import com.movies.repository.MovieRepository;
import com.movies.repository.TheatreRepository;
import com.movies.repository.entities.Movie;
import com.movies.repository.entities.Show;
import com.movies.repository.ShowRepository;
import com.movies.repository.entities.Theatre;
import com.movies.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowServiceImpl implements ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<ShowDTO> browseShows(Long movieId, String city, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        List<Show> shows = showRepository.findByMovie_IdAndTheatre_CityAndShowTimeBetween(movieId, city, start, end);

        return shows.stream().map(show -> ShowDTO.builder()
                .theatreName(show.getTheatre().getName())
                .movieTitle(show.getMovie().getTitle())
                .showTime(show.getShowTime())
                .availableSeats(show.getAvailableSeats())
                .build())
            .collect(Collectors.toList());
    }

    @Override
    public ShowDTO updateShow(Long id, ShowDTO dto) {
        Show show = showRepository.findById(id).orElseThrow(() -> new RuntimeException("Show not found"));
        Theatre theatre = theatreRepository.findById(dto.getTheatreId()).orElseThrow(() -> new RuntimeException("Theatre not found"));
        Movie movie = movieRepository.findById(dto.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found"));
        show.setTheatre(theatre);
        show.setMovie(movie);
        show.setShowTime(dto.getShowTime());
        show.setAvailableSeats(dto.getAvailableSeats());
        show = showRepository.save(show);
        dto.setId(show.getId());
        return dto;
    }

    @Override
    public ShowDTO createShow(ShowDTO dto) {
        Theatre theatre = theatreRepository.findById(dto.getTheatreId()).orElseThrow(() -> new RuntimeException("Theatre not found"));
        Movie movie = movieRepository.findById(dto.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found"));
        Show show = Show.builder()
                .theatre(theatre)
                .movie(movie)
                .showTime(dto.getShowTime())
                .availableSeats(dto.getAvailableSeats())
                .build();
        show = showRepository.save(show);
        dto.setId(show.getId());
        return dto;
    }

    @Override
    public void deleteShow(Long id) {
        showRepository.deleteById(id);
    }

    @Override
    public List<ShowDTO> getAllShows() {
        return showRepository.findAll().stream().map(s -> {
            ShowDTO dto = new ShowDTO();
            dto.setId(s.getId());
            dto.setTheatreId(s.getTheatre().getId());
            dto.setMovieId(s.getMovie().getId());
            dto.setShowTime(s.getShowTime());
            dto.setAvailableSeats(s.getAvailableSeats());
            return dto;
        }).collect(java.util.stream.Collectors.toList());
    }
}
