package com.example.myspringproject.service;

import com.example.myspringproject.service.exception.AuthenticationFailedException;
import com.example.myspringproject.web.dto.UserLoginDto;
import com.example.myspringproject.web.dto.requests.LoginRequest;

public interface AuthenticationService {
    UserLoginDto authenticate(LoginRequest loginRequest) throws AuthenticationFailedException;

}
