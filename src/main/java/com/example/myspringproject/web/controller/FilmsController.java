package com.example.myspringproject.web.controller;

import com.example.myspringproject.service.FilmsService;
import com.example.myspringproject.web.dto.requests.RequestBookByTitleFromApis;
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

    @PostMapping()
    public ResponseEntity<?> getFilmByTitleFromAPI(@RequestBody RequestBookByTitleFromApis request) throws IOException, InterruptedException, UnirestException {
        filmsService.getFilmsFromAPI(request.getTitle());
        return ResponseEntity.ok("ok");
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(filmsService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        filmsService.deleteFilmById(id);
        return ResponseEntity.ok("This book was delete");
    }

    @GetMapping("/top100")
    public ResponseEntity<?> getTop100Films() throws Exception {
        return ResponseEntity.ok(filmsService.getTop100Films());
    }

    @GetMapping("/{idIMDb}")
    public ResponseEntity<?> getFilmByIdIMDb(@PathVariable String idIMDb) throws IOException, InterruptedException {
        return ResponseEntity.ok(filmsService.getFilmByIdIMDb(idIMDb));
    }
}
