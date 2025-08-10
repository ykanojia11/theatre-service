package com.movies.controller;

import com.movies.dto.TheatreDTO;
import com.movies.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/theatres")
@Validated
public class TheatreController {
    private final TheatreService theatreService;

    @Autowired
    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @PreAuthorize("hasRole('PARTNER')")
    @PostMapping
    public TheatreDTO createTheatre(@RequestBody @Validated TheatreDTO dto) {
        return theatreService.createTheatre(dto);
    }

    @PreAuthorize("hasRole('PARTNER')")
    @PutMapping("/{id}")
    public TheatreDTO updateTheatre(@PathVariable Long id, @RequestBody @Validated TheatreDTO dto) {
        try {
            return theatreService.updateTheatre(id, dto);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('PARTNER')")
    @DeleteMapping("/{id}")
    public void deleteTheatre(@PathVariable Long id) {
        theatreService.deleteTheatre(id);
    }

    @PreAuthorize("hasAnyRole('PARTNER', 'CUSTOMER')")
    @GetMapping
    public List<TheatreDTO> getAllTheatres() {
        return theatreService.getAllTheatres();
    }
}
