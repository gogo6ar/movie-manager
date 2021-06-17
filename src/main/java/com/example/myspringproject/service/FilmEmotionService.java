package com.example.myspringproject.service;

import com.example.myspringproject.service.exception.UserAlreadyVotesException;
import com.example.myspringproject.web.dto.requests.FilmEmotionRequest;

public interface FilmEmotionService {
    public void addEmotion(FilmEmotionRequest request) throws UserAlreadyVotesException;
}
