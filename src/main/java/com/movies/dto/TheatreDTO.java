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
public class TheatreDTO {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String city;
    private String address;
}
