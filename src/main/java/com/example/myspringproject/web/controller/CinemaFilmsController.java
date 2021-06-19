package com.example.myspringproject.web.controller;

import com.example.myspringproject.service.CinemaParserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "**")
@RequestMapping("/cinema")
@RequiredArgsConstructor
public class CinemaFilmsController {
    private final CinemaParserServiceImpl cinemaParserService;

    //example: 2021-06-20
    @GetMapping("/{date}")
    public ResponseEntity<?> getCinemaFilms(@PathVariable String date) throws IOException {
        return ResponseEntity.ok(cinemaParserService.getCinemaFilms(date));
    }
}
