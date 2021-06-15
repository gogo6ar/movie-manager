package com.example.myspringproject.service;

import com.example.myspringproject.repo.FilmEmotionRepository;
import com.example.myspringproject.repo.FilmRepository;
import com.example.myspringproject.repo.UserRepository;
import com.example.myspringproject.web.dto.requests.FilmEmotionRequest;
import com.example.myspringproject.web.entity.EmotionType;
import com.example.myspringproject.web.entity.FilmEmotion;
import com.example.myspringproject.web.entity.Films;
import com.example.myspringproject.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmEmotionServiceImpl implements FilmEmotionService {
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final FilmEmotionRepository filmEmotionRepository;

    @Override
    public Map<EmotionType, Integer> addEmotion(FilmEmotionRequest request) {
        HashMap<EmotionType, Integer> map = new HashMap<>();
        Optional<User> user = userRepository.findById(request.getUserId());
        Optional<Films> films = filmRepository.findById(request.getFilmId());
        EmotionType emotion = null;

        if (request.getReaction().equals("SAD")) emotion = EmotionType.SAD;
        else if (request.getReaction().equals("HEART")) emotion = EmotionType.HEART;

        FilmEmotion filmEmotion = FilmEmotion.builder()
                .user(user.get())
                .films(films.get())
                .emotionType(emotion)
                .build();

        filmEmotionRepository.save(filmEmotion);
        return map;
    }
}
