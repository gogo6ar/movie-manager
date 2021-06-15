package com.example.myspringproject.web.dto.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class FilmEmotionRequest {
    private Long userId;
    private Long filmId;
    private String reaction;

    @JsonCreator
    public FilmEmotionRequest (@JsonProperty Long userId,
                               @JsonProperty Long filmId,
                               @JsonProperty String reaction) {
        this.filmId = filmId;
        this.userId = userId;
        this.reaction = reaction;
    }
}
