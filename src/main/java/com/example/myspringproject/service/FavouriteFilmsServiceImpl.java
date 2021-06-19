package com.example.myspringproject.service;

import com.example.myspringproject.repo.FavouriteFilmsRepository;
import com.example.myspringproject.repo.FilmRepository;
import com.example.myspringproject.repo.UserRepository;
import com.example.myspringproject.web.dto.requests.FavouriteFilmsRequest;
import com.example.myspringproject.web.entity.FavouritesFilms;
import com.example.myspringproject.web.entity.Films;
import com.example.myspringproject.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavouriteFilmsServiceImpl implements FavouriteFilmsService{
    private final FavouriteFilmsRepository favouriteFilms;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

//    @Override
//    public List<FilmsDto> getFavouritesFilms(FavouritesFilms request) {
//        List<FilmsDto> listOfFavouriteFilms = new ArrayList<>();
//        List<Films> listOfFilms = favouriteFilms.getFavouritesFilms(request.getId());
//
//        for (Films film : listOfFilms) {
//            listOfFavouriteFilms.add(FilmsDto.from(film, film.getComments()));
//        }
//
//        return listOfFavouriteFilms;
//    }

    @Override
    public void addFavouriteFilm(FavouriteFilmsRequest request) {
        Films films = filmRepository.getById(request.getFilmsId());
        User user = userRepository.getById(request.getUserId());

        FavouritesFilms favouritesFilms = FavouritesFilms.builder()
                .films(films)
                .user(user)
                .build();
        favouriteFilms.save(favouritesFilms);
    }
}
