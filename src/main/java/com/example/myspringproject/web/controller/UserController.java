package com.example.myspringproject.web.controller;

import com.example.myspringproject.repo.UserRepository;
import com.example.myspringproject.service.UserService;
import com.example.myspringproject.web.dto.requests.RegisterRequest;
import com.example.myspringproject.web.dto.requests.UpdateUserRequest;
import com.example.myspringproject.web.dto.requests.UserVoteRequest;
import com.example.myspringproject.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "**")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest request) {
        User user = userService.create(request);
        System.out.println(user.getListOfUserRating());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id,
                                            @RequestBody UpdateUserRequest request) {
        userService.updateUser(id, request);
        return ResponseEntity.ok().build();
    }

}
