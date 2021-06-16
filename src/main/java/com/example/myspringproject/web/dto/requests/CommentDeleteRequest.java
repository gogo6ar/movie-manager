package com.example.myspringproject.web.dto.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDeleteRequest {
    private Long userId;
    private Long commentId;

    @JsonCreator
    public CommentDeleteRequest(@JsonProperty Long userId,
                                @JsonProperty Long commentId) {
        this.userId = userId;
        this.commentId = commentId;
    }
}
