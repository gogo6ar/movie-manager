package com.example.myspringproject.web.controller;

import com.example.myspringproject.service.UserRatingService;
import com.example.myspringproject.service.UserService;
import com.example.myspringproject.web.dto.requests.UserVoteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "**")
@RequestMapping("/vote")
@RequiredArgsConstructor
public class VoteController {
    private final UserService userService;
    private final UserRatingService userRatingService;

    @PostMapping("/user")
    public ResponseEntity<?> vote(@RequestBody UserVoteRequest request) throws Exception {
        userRatingService.vote(request);
        return ResponseEntity.ok().build();
    }
}
