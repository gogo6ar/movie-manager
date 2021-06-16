package com.example.myspringproject.web.controller;

import com.example.myspringproject.repo.FilmEmotionRepository;
import com.example.myspringproject.repo.FilmRepository;
import com.example.myspringproject.service.FilmEmotionService;
import com.example.myspringproject.service.exception.UserAlreadyVotesException;
import com.example.myspringproject.web.dto.FilmsDto;
import com.example.myspringproject.web.dto.requests.FilmEmotionRequest;
import com.example.myspringproject.web.entity.EmotionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "**")
@RequestMapping("/emotion")
@RequiredArgsConstructor
public class FilmsEmotionController {
    private final FilmEmotionService filmEmotionService;
    private final FilmRepository filmRepository;

    @PostMapping()
    public ResponseEntity<?> addEmotion (@RequestBody FilmEmotionRequest request) throws UserAlreadyVotesException {
        filmEmotionService.addEmotion(request);
        FilmsDto filmsDto = FilmsDto.from(filmRepository.findById(request.getFilmId()).get());
        Map<EmotionType, Integer> map = filmsDto.getEmotionsCount();
        return ResponseEntity.ok(map);
    }
}
