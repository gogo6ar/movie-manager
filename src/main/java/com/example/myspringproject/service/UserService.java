package com.example.myspringproject.service;

import com.example.myspringproject.web.dto.UserDto;
import com.example.myspringproject.web.dto.requests.RegisterRequest;
import com.example.myspringproject.web.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserDto> findAll();

    User create(RegisterRequest request);

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}
