package com.example.myspringproject.service;

import com.example.myspringproject.web.dto.requests.CommentRequest;

public interface CommentService {
    void addComment(CommentRequest request);
}
