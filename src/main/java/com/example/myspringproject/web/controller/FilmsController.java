package com.example.myspringproject.web.controller;

import com.example.myspringproject.service.FilmsService;
import com.example.myspringproject.web.dto.FilmsDto;
import com.example.myspringproject.web.dto.requests.RequestBookByTitleFromApis;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "**")
@RequestMapping("/movies")
@RequiredArgsConstructor
public class FilmsController {
    private final FilmsService filmsService;

    @PostMapping()
    public ResponseEntity<?> getFilmByTitleFromAPI(@RequestBody RequestBookByTitleFromApis request) throws IOException, InterruptedException, UnirestException {
        filmsService.getFilmsFromAPI(request.getTitle());
        return ResponseEntity.ok("ok");
    }

    @GetMapping()
    public ResponseEntity<?> getAllFilms() {
        return ResponseEntity.ok(filmsService.getAll());
    }

}
