package com.example.myspringproject.web.dto.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RequestFilmByTitleFromApis {
    private String title;

    @JsonCreator
    public RequestFilmByTitleFromApis(@JsonProperty String title) {
        this.title = title;
    }
}
