package com.example.myspringproject.web.controller;

import com.example.myspringproject.service.CinemaParserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "**")
@RequestMapping("/cinema")
@RequiredArgsConstructor
public class CinemaFilmsController {
    private final CinemaParserServiceImpl cinemaParserService;

    @GetMapping()
    public ResponseEntity<?> getCinemaFilms() throws IOException {
        cinemaParserService.saveCinemaFilms();
        return ResponseEntity.ok().build();
    }
}
