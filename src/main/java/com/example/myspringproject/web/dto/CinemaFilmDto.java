package com.example.myspringproject.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CinemaFilmDto {
    private String title;
    private String moreInfoLink;
    private String imgLink;
    private String date;
    private String time;
}
