package com.movies.service.impl;

import com.movies.dto.TheatreDTO;
import com.movies.repository.TheatreRepository;
import com.movies.repository.entities.Theatre;
import com.movies.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheatreServiceImpl implements TheatreService {
    private final TheatreRepository theatreRepository;

    @Autowired
    public TheatreServiceImpl(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }

    @Override
    public TheatreDTO createTheatre(TheatreDTO dto) {
        Theatre theatre = Theatre.builder()
                .name(dto.getName())
                .city(dto.getCity())
                .address(dto.getAddress())
                .build();
        theatre = theatreRepository.save(theatre);
        dto.setId(theatre.getId());
        return dto;
    }

    @Override
    public TheatreDTO updateTheatre(Long id, TheatreDTO dto) {
        Theatre theatre = theatreRepository.findById(id).orElseThrow(() -> new RuntimeException("Theatre not found"));
        theatre.setName(dto.getName());
        theatre.setCity(dto.getCity());
        theatre.setAddress(dto.getAddress());
        theatre = theatreRepository.save(theatre);
        dto.setId(theatre.getId());
        return dto;
    }

    @Override
    public void deleteTheatre(Long id) {
        theatreRepository.deleteById(id);
    }

    @Override
    public List<TheatreDTO> getAllTheatres() {
        return theatreRepository.findAll().stream().map(t -> {
            TheatreDTO dto = new TheatreDTO();
            dto.setId(t.getId());
            dto.setName(t.getName());
            dto.setCity(t.getCity());
            dto.setAddress(t.getAddress());
            return dto;
        }).collect(Collectors.toList());
    }
}
