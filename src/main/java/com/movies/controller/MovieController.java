package com.movies.controller;

import com.movies.dto.MovieDTO;
import com.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/movies")
@Validated
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PreAuthorize("hasRole('PARTNER')")
    @PostMapping
    public MovieDTO createMovie(@RequestBody @Validated MovieDTO dto) {
        return movieService.createMovie(dto);
    }

    @PreAuthorize("hasRole('PARTNER')")
    @PutMapping("/{id}")
    public MovieDTO updateMovie(@PathVariable Long id, @RequestBody @Validated MovieDTO dto) {
        try {
            return movieService.updateMovie(id, dto);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('PARTNER')")
    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }

    @PreAuthorize("hasAnyRole('PARTNER', 'CUSTOMER')")
    @GetMapping
    public List<MovieDTO> getAllMovies() {
        return movieService.getAllMovies();
    }
}
