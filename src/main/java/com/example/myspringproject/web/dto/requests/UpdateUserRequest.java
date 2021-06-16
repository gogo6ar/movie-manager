package com.example.myspringproject.web.dto.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String role;

    @JsonCreator
    public UpdateUserRequest (@JsonProperty String firstName,
                              @JsonProperty String lastName,
                              @JsonProperty String email,
                              @JsonProperty String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }
}
