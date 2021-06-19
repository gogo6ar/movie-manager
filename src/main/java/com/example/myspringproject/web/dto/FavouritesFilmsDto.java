package com.example.myspringproject.web.dto;

import com.example.myspringproject.web.entity.FavouritesFilms;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavouritesFilmsDto {
    private Long id;
    private UserDto userDto;
    private FilmsDto filmsDto;

    public static FavouritesFilmsDto from(FavouritesFilms favouritesFilms) {
        FilmsDto filmsDto = FilmsDto.from(favouritesFilms.getFilms(), favouritesFilms.getFilms().getComments());
        FavouritesFilmsDto result = FavouritesFilmsDto.builder()
                .id(favouritesFilms.getId())
                .filmsDto(filmsDto)
                .build();

        return result;
    }
}
