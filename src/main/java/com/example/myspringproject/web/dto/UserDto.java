package com.example.myspringproject.web.dto;

import com.example.myspringproject.web.entity.FavouritesFilms;
import com.example.myspringproject.web.entity.User;
import com.example.myspringproject.web.entity.UserRating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private Double rating;
    private List<UserRatingDto> listOfUserRating;
    private List<FavouritesFilmsDto> listOfFavouriteFilms;

    public static UserDto from(User user, List<UserRating> ratings) {
        List<UserRatingDto> userRatingsDtos = new ArrayList<>();
        if (ratings != null) {
            for (UserRating rating : ratings) {
                UserRatingDto userRatingDto = UserRatingDto.from(rating);
                userRatingsDtos.add(userRatingDto);
            }
        }
        UserDto result = from(user);
        result.setListOfUserRating(userRatingsDtos);

        return result;
    }

    public static UserDto from(User user) {
        List<FavouritesFilmsDto> listOfFavouriteFilmDto = new ArrayList<>();
        List<FavouritesFilms> listOfFavouriteFilms = user.getListOfFavouritesFilms();
        if (listOfFavouriteFilms != null) {
            for (FavouritesFilms e : listOfFavouriteFilms) {
                listOfFavouriteFilmDto.add(FavouritesFilmsDto.from(e));
            }
        }

        UserDto result = new UserDto();
        result.setId(user.getId());
        result.setEmail(user.getEmail());
        result.setFirstName(user.getFirstName());
        result.setLastName(user.getLastName());
        result.setRole(user.getRole());
        result.setRating(user.getRating());
        result.setListOfFavouriteFilms(listOfFavouriteFilmDto);
        return result;
    }
}
