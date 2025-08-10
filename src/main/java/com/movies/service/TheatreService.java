package com.movies.service;

import com.movies.dto.TheatreDTO;
import java.util.List;

public interface TheatreService {
    TheatreDTO createTheatre(TheatreDTO dto);
    TheatreDTO updateTheatre(Long id, TheatreDTO dto);
    void deleteTheatre(Long id);
    List<TheatreDTO> getAllTheatres();
}
