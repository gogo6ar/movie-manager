package com.example.myspringproject.service;

import com.example.myspringproject.web.dto.UserDto;
import com.example.myspringproject.web.dto.requests.RegisterRequest;
import com.example.myspringproject.web.dto.requests.UpdateUserRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserDto> findAll();

    UserDto create(RegisterRequest request);

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    void updateUser(Long id, UpdateUserRequest request);

    void deleteUserById(Long id);

    UserDto getUserById(Long id);

    List<UserDto> getTop10Users();
}
