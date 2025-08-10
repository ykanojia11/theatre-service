package com.movies.service;

import com.movies.dto.ShowDTO;

import java.time.LocalDate;
import java.util.List;

public interface ShowService {
    List<ShowDTO> browseShows(Long movieId, String city, LocalDate date);
    ShowDTO updateShow(Long id, ShowDTO dto);
    ShowDTO createShow(ShowDTO dto);
    void deleteShow(Long id);
    List<ShowDTO> getAllShows();
}
