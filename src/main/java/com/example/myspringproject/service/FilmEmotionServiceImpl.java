package com.example.myspringproject.service;

import com.example.myspringproject.repo.FilmEmotionRepository;
import com.example.myspringproject.repo.FilmRepository;
import com.example.myspringproject.repo.UserRepository;
import com.example.myspringproject.service.exception.UserAlreadyVotesException;
import com.example.myspringproject.web.dto.FilmsDto;
import com.example.myspringproject.web.dto.requests.FilmEmotionRequest;
import com.example.myspringproject.web.entity.EmotionType;
import com.example.myspringproject.web.entity.FilmEmotion;
import com.example.myspringproject.web.entity.Films;
import com.example.myspringproject.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FilmEmotionServiceImpl implements FilmEmotionService {
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final FilmEmotionRepository filmEmotionRepository;

    @Override
    public void addEmotion(FilmEmotionRequest request) throws UserAlreadyVotesException {
        Optional<User> user = userRepository.findById(request.getUserId());
        Optional<Films> films = filmRepository.findById(request.getFilmId());
        EmotionType emotion = null;

        List<Long> list = new ArrayList<>(filmEmotionRepository
                .getUsersIdFromEmotions(request.getFilmId()));
        for (Long id : list) {
            if (request.getUserId() == id) {
                throw new UserAlreadyVotesException("This user already votes");
            }
        }

        if (request.getReaction().equals("SAD")) emotion = EmotionType.SAD;
        else if (request.getReaction().equals("HEART")) emotion = EmotionType.HEART;

        FilmEmotion filmEmotion = FilmEmotion.builder()
                .user(user.get())
                .films(films.get())
                .emotionType(emotion)
                .build();

        filmEmotionRepository.save(filmEmotion);
    }
}
