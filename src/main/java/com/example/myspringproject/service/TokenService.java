package com.example.myspringproject.service;

import com.example.myspringproject.web.entity.User;

import java.util.Map;

public interface TokenService {

    Map<String, String> getUserDataFromToken(String token);

    String createToken(User user);
}
