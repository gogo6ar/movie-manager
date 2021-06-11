package com.example.myspringproject.web.dto;

import com.example.myspringproject.web.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserLoginDto {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private String role;
    private String bearer;

    public static UserLoginDto from(User user, String bearer) {
        UserLoginDto result = new UserLoginDto();
        result.setId(user.getId());
        result.setEmail(user.getEmail());
        result.setFirstName(user.getFirstName());
        result.setLastName(user.getLastName());
        result.setRole(user.getRole());
        result.setBearer(bearer);
        return result;
    }
}
