package com.example.myspringproject.web.dto.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatePasswordRequest {
    private String lastPassword;
    private String newPassword;

    @JsonCreator
    public UpdatePasswordRequest(@JsonProperty String lastPassword,
                                @JsonProperty String newPassword) {
        this.lastPassword = lastPassword;
        this.newPassword = newPassword;
    }
}
