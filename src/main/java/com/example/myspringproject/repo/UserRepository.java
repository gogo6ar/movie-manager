package com.example.myspringproject.repo;

import com.example.myspringproject.web.dto.requests.UpdateUserRequest;
import com.example.myspringproject.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByRole(String role);

    Integer countUsersByIdGreaterThan(Long start);

}
