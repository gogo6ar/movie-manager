package com.example.myspringproject.web.dto.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FavouriteFilmsRequest {
    private Long userId;
    private Long filmsId;

    @JsonCreator
    public FavouriteFilmsRequest (@JsonProperty Long userId,
                            @JsonProperty Long filmsId) {
        this.filmsId = filmsId;
        this.userId = userId;
    }
}
