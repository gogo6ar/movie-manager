package com.example.myspringproject.service;

import com.example.myspringproject.web.dto.FilmsDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;

public interface FilmsService {
    FilmsDto getFilmsFromAPI(String title) throws IOException, InterruptedException, UnirestException;
    void saveFilm(JsonNode items);
}
