package com.example.myspringproject.service;

import com.example.myspringproject.web.dto.CinemaFilmDto;

import java.io.IOException;
import java.util.List;

public interface CinemaParserService {
    public List<CinemaFilmDto> getCinemaFilms(String date) throws IOException;
}
