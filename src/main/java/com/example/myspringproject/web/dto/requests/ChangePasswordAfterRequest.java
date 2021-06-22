package com.example.myspringproject.web.dto.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordAfterRequest {
    private String newPassword;

    @JsonCreator
    public ChangePasswordAfterRequest(@JsonProperty String newPassword) {
        this.newPassword = newPassword;
    }
}
