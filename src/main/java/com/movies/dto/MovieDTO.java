package com.movies.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private Long id;
    @NotBlank
    private String title;
    private String language;
    private String genre;
}
