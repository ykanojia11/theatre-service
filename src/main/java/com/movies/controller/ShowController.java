package com.movies.controller;

import com.movies.dto.ShowDTO;
import com.movies.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/shows")
@Validated
public class ShowController {
    private final ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PreAuthorize("hasRole('PARTNER')")
    @PostMapping
    public ShowDTO createShow(@RequestBody @Validated ShowDTO dto) {
        return showService.createShow(dto);
    }

    @PreAuthorize("hasRole('PARTNER')")
    @PutMapping("/{id}")
    public ShowDTO updateShow(@PathVariable Long id, @RequestBody @Validated ShowDTO dto) {
        try {
            return showService.updateShow(id, dto);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('PARTNER')")
    @DeleteMapping("/{id}")
    public void deleteShow(@PathVariable Long id) {
        showService.deleteShow(id);
    }

    @PreAuthorize("hasAnyRole('PARTNER', 'CUSTOMER')")
    @GetMapping
    public List<ShowDTO> getAllShows() {
        return showService.getAllShows();
    }

    @PreAuthorize("hasAnyRole('PARTNER', 'CUSTOMER')")
    @GetMapping("/browse")
    public List<ShowDTO> browseShows(
            @RequestParam Long movieId,
            @RequestParam String city,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return showService.browseShows(movieId, city, date);
    }
}
