package com.example.myspringproject.service;

import com.example.myspringproject.web.dto.CommentDto;
import com.example.myspringproject.web.dto.requests.CommentRequest;
import com.example.myspringproject.web.entity.Comment;

import java.util.List;

public interface CommentService {
    void addComment(CommentRequest request);
}
