package com.example.myspringproject.web.dto;

import com.example.myspringproject.web.entity.Films;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilmsDto {
    private Long id;
    private String title;
    private String idIMDb;
    private String imgLink;
    private String numberOfEpisodes;
    private String titleType;
    private String year;

    public static FilmsDto from(Films films) {
        FilmsDto result = new FilmsDto();
        result.setId(films.getId());
        result.setTitle(films.getTitle());
        result.setIdIMDb(films.getIdIMDb());
        result.setImgLink(films.getImgLink());
        result.setTitleType(films.getTitleType());
        result.setYear(films.getYear());
        result.setNumberOfEpisodes(films.getNumberOfEpisodes());
        return result;
    }
}
