package com.example.myspringproject.repo;

import com.example.myspringproject.web.entity.FavouritesFilms;
import com.example.myspringproject.web.entity.Films;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavouriteFilmsRepository extends JpaRepository<FavouritesFilms, Long> {

    @Query(nativeQuery = true, value = "SELECT f.film_id FROM favourites_films f WHERE f.user_id = :id")
    List<Films> getFavouritesFilms(Long id);

    void deleteAllByFilmsId(Long filmsId);

    void deleteAllByUserId(Long id);
}
