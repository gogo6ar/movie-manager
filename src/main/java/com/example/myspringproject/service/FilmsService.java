package com.example.myspringproject.service;

import com.example.myspringproject.web.dto.FilmsDto;

import java.io.IOException;

public interface FilmsService {
    FilmsDto getFilmsFromAPI(String title) throws IOException, InterruptedException;
}
