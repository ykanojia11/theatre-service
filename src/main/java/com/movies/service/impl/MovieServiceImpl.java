package com.movies.service.impl;

import com.movies.dto.MovieDTO;
import com.movies.repository.MovieRepository;
import com.movies.repository.entities.Movie;
import com.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public MovieDTO createMovie(MovieDTO dto) {
        Movie movie = Movie.builder()
                .title(dto.getTitle())
                .language(dto.getLanguage())
                .genre(dto.getGenre())
                .build();
        movie = movieRepository.save(movie);
        dto.setId(movie.getId());
        return dto;
    }

    @Override
    public MovieDTO updateMovie(Long id, MovieDTO dto) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setTitle(dto.getTitle());
        movie.setLanguage(dto.getLanguage());
        movie.setGenre(dto.getGenre());
        movie = movieRepository.save(movie);
        dto.setId(movie.getId());
        return dto;
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll().stream().map(m -> {
            MovieDTO dto = new MovieDTO();
            dto.setId(m.getId());
            dto.setTitle(m.getTitle());
            dto.setLanguage(m.getLanguage());
            dto.setGenre(m.getGenre());
            return dto;
        }).collect(Collectors.toList());
    }
}
