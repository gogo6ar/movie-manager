package com.example.myspringproject.web.controller;

import com.example.myspringproject.service.FilmsService;
import com.example.myspringproject.web.dto.requests.AddFilmRequest;
import com.example.myspringproject.web.dto.requests.RequestFilmByTitleFromApis;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

@RestController
@CrossOrigin(origins = "**")
@RequestMapping("/movies")
@RequiredArgsConstructor
public class FilmsController {
    private final FilmsService filmsService;

    @PostMapping()
    public ResponseEntity<?> getFilmByTitleFromAPI(@RequestBody RequestFilmByTitleFromApis request) throws IOException, InterruptedException, UnirestException {
        return ResponseEntity.ok(filmsService.getFilmsFromAPI(request.getTitle()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFilm(@RequestBody AddFilmRequest request) throws FileAlreadyExistsException {
        filmsService.addFilms(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(filmsService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        filmsService.deleteFilmById(id);
        return ResponseEntity.ok("This book was delete");
    }

    @GetMapping("/id={id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(filmsService.getById(id));
    }

    @GetMapping("/top100")
    public ResponseEntity<?> getTop100Films() throws Exception {
        return ResponseEntity.ok(filmsService.getTop100Films());
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<?> getFilmByTitleFromDataBase(@PathVariable String title) {
        return ResponseEntity.ok(filmsService.getFilmByTitleFromDataBase(title));
    }

    @GetMapping("/idIMDb={idIMDb}")
    public ResponseEntity<?> getFilmByIdIMDb(@PathVariable String idIMDb) throws IOException, InterruptedException {
        return ResponseEntity.ok(filmsService.getFilmByIdIMDb(idIMDb));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getFilmsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(filmsService.getFilmsByCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity <?> updateFilm(@RequestBody AddFilmRequest request,
                                         @PathVariable("id") Long filmId) throws FileAlreadyExistsException {
        filmsService.updateFilm(request, filmId);
        return ResponseEntity.ok().build();
    }
}
