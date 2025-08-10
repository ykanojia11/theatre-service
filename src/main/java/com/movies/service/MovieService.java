package com.movies.service;

import com.movies.dto.MovieDTO;
import java.util.List;

public interface MovieService {
    MovieDTO createMovie(MovieDTO dto);
    MovieDTO updateMovie(Long id, MovieDTO dto);
    void deleteMovie(Long id);
    List<MovieDTO> getAllMovies();
}
