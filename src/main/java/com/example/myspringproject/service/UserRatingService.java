package com.example.myspringproject.service;

import com.example.myspringproject.web.dto.requests.UserVoteRequest;

public interface UserRatingService {
    public void vote(UserVoteRequest request) throws Exception;
}
