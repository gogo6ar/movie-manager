package com.example.myspringproject.service.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UserAlreadyVotesException extends Exception {
    public UserAlreadyVotesException (String message) {super(message);}
}
