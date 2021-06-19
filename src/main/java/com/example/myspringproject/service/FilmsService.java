package com.example.myspringproject.service;

import com.example.myspringproject.web.dto.FilmsDto;
import com.example.myspringproject.web.dto.requests.AddFilmRequest;
import com.example.myspringproject.web.entity.Films;
import com.fasterxml.jackson.databind.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Map;

public interface FilmsService {
    List<FilmsDto> getFilmsFromAPI(String title) throws IOException, InterruptedException, UnirestException;

    void saveFilm(Films film);

    Films getItemFilm(JsonNode items);

    Films getFilmByIdIMDb(String id) throws IOException, InterruptedException;

    List<FilmsDto> findAll();

    void deleteFilmById(Long id);

    Map<Byte, String> getTop100Films() throws Exception;

    List<FilmsDto> getFilmByTitleFromDataBase(String title);

    void addFilms(AddFilmRequest request) throws FileAlreadyExistsException;

    void updateFilm(AddFilmRequest request, Long filmId) throws FileAlreadyExistsException;
}
