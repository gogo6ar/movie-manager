package com.example.myspringproject.web.dto.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserVoteRequest {
    private Long userId;
    private Long userVoteId;
    private byte rating;

    @JsonCreator
    public UserVoteRequest(@JsonProperty Long userId,
                           @JsonProperty Long userVoteId,
                           @JsonProperty byte rating) {
        this.userId = userId;
        this.userVoteId = userVoteId;
        this.rating = rating;
    }
}
