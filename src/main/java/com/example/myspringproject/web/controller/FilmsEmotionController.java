package com.example.myspringproject.web.controller;

import com.example.myspringproject.service.FilmEmotionService;
import com.example.myspringproject.web.dto.requests.FilmEmotionRequest;
import com.example.myspringproject.web.entity.FilmEmotion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "**")
@RequestMapping("/emotion")
@RequiredArgsConstructor
public class FilmsEmotionController {
    private final FilmEmotionService filmEmotionService;

    @PostMapping()
    public ResponseEntity<?> addEmotion (@RequestBody FilmEmotionRequest request) {
        return ResponseEntity.ok(filmEmotionService.addEmotion(request));
    }
}
