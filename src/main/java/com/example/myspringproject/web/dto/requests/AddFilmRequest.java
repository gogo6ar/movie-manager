package com.example.myspringproject.web.dto.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddFilmRequest {
    private String title;
    private String idIMDb;
    private String imgLink;
    private Integer numberOfEpisodes;
    private String titleType;
    private Integer year;
    private Long userId;

    @JsonCreator
    public AddFilmRequest( @JsonProperty String title,
                           @JsonProperty String idIMDb,
                           @JsonProperty String imgLink,
                           @JsonProperty Integer numberOfEpisodes,
                           @JsonProperty String titleType,
                           @JsonProperty Integer year,
                           @JsonProperty Long userId
                          ) {
        this.title = title;
        this.idIMDb = idIMDb;
        this.imgLink = imgLink;
        this.numberOfEpisodes =numberOfEpisodes;
        this.titleType = titleType;
        this.year = year;
        this.userId = userId;
    }
}
