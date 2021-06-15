package com.example.myspringproject.web.dto.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRequest {
    private Long userId;
    private Long filmId;
    private String comment;


    @JsonCreator
    public CommentRequest (@JsonProperty Long userId,
                         @JsonProperty Long filmId,
                           @JsonProperty String comment) {
        this.userId = userId;
        this.filmId = filmId;
        this.comment = comment;
    }
}
