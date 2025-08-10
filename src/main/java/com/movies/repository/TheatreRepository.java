package com.movies.repository;

import com.movies.repository.entities.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {
}
