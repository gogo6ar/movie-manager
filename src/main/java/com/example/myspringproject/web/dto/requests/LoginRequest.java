package com.example.myspringproject.web.dto.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    private String email;
    private String password;

    @JsonCreator
    public LoginRequest (@JsonProperty String email,
                         @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }
}
