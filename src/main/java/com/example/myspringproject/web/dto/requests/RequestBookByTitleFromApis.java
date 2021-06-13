package com.example.myspringproject.web.dto.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RequestBookByTitleFromApis {
    private String title;

    @JsonCreator
    public RequestBookByTitleFromApis(@JsonProperty String title) {
        this.title = title;
    }
}
