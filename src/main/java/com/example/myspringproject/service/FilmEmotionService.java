package com.example.myspringproject.service;

import com.example.myspringproject.web.dto.requests.FilmEmotionRequest;
import com.example.myspringproject.web.entity.EmotionType;

import java.util.Map;

public interface FilmEmotionService {
    public Map<EmotionType, Integer> addEmotion(FilmEmotionRequest request);
}
