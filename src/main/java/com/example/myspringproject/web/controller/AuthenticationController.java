package com.example.myspringproject.web.controller;

import com.example.myspringproject.service.AuthenticationService;
import com.example.myspringproject.web.dto.UserLoginDto;
import com.example.myspringproject.web.dto.requests.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "**")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UserLoginDto authenticatedUser = authenticationService.authenticate(loginRequest);
        return ResponseEntity.ok(authenticatedUser);
    }
}
