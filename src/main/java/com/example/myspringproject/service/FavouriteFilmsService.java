package com.example.myspringproject.service;

import com.example.myspringproject.web.dto.FilmsDto;
import com.example.myspringproject.web.dto.requests.FavouriteFilmsRequest;
import com.example.myspringproject.web.entity.FavouritesFilms;

import java.util.List;

public interface FavouriteFilmsService {

    void addFavouriteFilm(FavouriteFilmsRequest request);
}
