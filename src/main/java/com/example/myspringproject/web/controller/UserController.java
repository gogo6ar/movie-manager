package com.example.myspringproject.web.controller;

import com.example.myspringproject.repo.UserRepository;
import com.example.myspringproject.service.UserService;
import com.example.myspringproject.web.dto.requests.RegisterRequest;
import com.example.myspringproject.web.dto.requests.UpdatePasswordRequest;
import com.example.myspringproject.web.dto.requests.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

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

    @GetMapping()
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest request) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(userService.create(request));
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id,
                                            @RequestBody UpdateUserRequest request) {
        userService.updateUser(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/password/{id}")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest request,
                                            @PathVariable Long id) throws Exception {
        userService.updatePassword(id, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset/password/{id}")
    public ResponseEntity<?> resetPassword(@RequestBody String password,
                                           @PathVariable Long id) throws Exception {
        userService.resetPassword(id, password);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/top10")
    public ResponseEntity<?> getTop10Users() {
        return ResponseEntity.ok(userService.getTop10Users());
    }
}
