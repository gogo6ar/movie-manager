package com.example.myspringproject.web.dto;

import com.example.myspringproject.web.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private long id; //
    private String email;
    private String firstName;
    private String lastName;
    private String role;

    public static UserDto from(User user) {
        UserDto result = new UserDto();
        result.setId(user.getId());
        result.setEmail(user.getEmail());
        result.setFirstName(user.getFirstName());
        result.setLastName(user.getLastName());
        result.setRole(user.getRole());
        return result;
    }
}
