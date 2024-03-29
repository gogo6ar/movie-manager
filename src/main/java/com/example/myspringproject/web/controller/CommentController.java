package com.example.myspringproject.web.controller;

import com.example.myspringproject.repo.CommentRepository;
import com.example.myspringproject.service.CommentService;
import com.example.myspringproject.web.dto.requests.CommentDeleteRequest;
import com.example.myspringproject.web.dto.requests.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "**")
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping()
    public ResponseEntity<?> addComment(@RequestBody CommentRequest request) {
        commentService.addComment(request);
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteComment(@RequestBody CommentDeleteRequest commentDeleteRequest) {
        commentRepository.deleteAllById(commentDeleteRequest.getCommentId());
        return ResponseEntity.ok().build();
    }
}
