package com.example.myspringproject.web.controller;

import com.example.myspringproject.service.FilmsService;
import com.example.myspringproject.web.dto.FilmsDto;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "**")
@RequestMapping("/movies")
@RequiredArgsConstructor
public class FilmsController {
    private final FilmsService filmsService;

    @GetMapping("/{title}")
    public ResponseEntity<?> getFilmByTitleFromAPI(@PathVariable("title") String title) throws IOException, InterruptedException, UnirestException {
        FilmsDto films = filmsService.getFilmsFromAPI(title);
        return ResponseEntity.ok(films);
    }


}
