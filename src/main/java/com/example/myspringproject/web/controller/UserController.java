package com.example.myspringproject.web.controller;

import com.example.myspringproject.repo.UserRepository;
import com.example.myspringproject.service.ResetPasswordService;
import com.example.myspringproject.service.UserService;
import com.example.myspringproject.web.dto.requests.ChangePasswordAfterRequest;
import com.example.myspringproject.web.dto.requests.RegisterRequest;
import com.example.myspringproject.web.dto.requests.UpdatePasswordRequest;
import com.example.myspringproject.web.dto.requests.UpdateUserRequest;
import com.example.myspringproject.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "**")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final ResetPasswordService resetPasswordService;

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

    @GetMapping("/reset-password/{id}")
    public ResponseEntity<?> resetPasswordEmail(@PathVariable Long id) throws MessagingException, UnsupportedEncodingException {
        resetPasswordService.resetPassword(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reset-password-check/{id}")
    public ResponseEntity<?> resetPasswordCheck(@PathVariable Long id,
                                                @Param("code") String code) {

        if (resetPasswordService.verify(code)) {
            Optional<User> user = userRepository.findById(id);
            user.get().setPassword(null);
            userRepository.save(user.get());

            return ResponseEntity.ok("Password reset");
        } else {
            return ResponseEntity.ok("password_reset_fail");
        }
    }

    @PutMapping("/reset-password/{id}")
    public ResponseEntity<?> changePasswordAfterReset(@PathVariable Long id,
                                                      @RequestBody ChangePasswordAfterRequest request) throws Exception {
        return ResponseEntity.ok(resetPasswordService.
                changePasswordAfterReset(id, request.getNewPassword()));
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
