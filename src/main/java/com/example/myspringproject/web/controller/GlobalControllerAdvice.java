package com.example.myspringproject.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

//    @ExceptionHandler
//    public ResponseEntity<?> handleRepeatedVoteException(RepeatedVoteException e) {
//        return ResponseEntity.badRequest().body(ErrorDto.from(e.getMessage()));
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
//        return ResponseEntity.badRequest().body(ErrorDto.from(e.getMessage()));
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<?> handleBookDuplicateException(BookDuplicateException e) {
//        return ResponseEntity.badRequest().body(ErrorDto.from(e.getMessage()));
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<?> handleSamePasswordException(SamePasswordException e) {
//        return ResponseEntity.badRequest().body(ErrorDto.from(e.getMessage()));
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
//        return ResponseEntity.badRequest().body(ErrorDto.from(e.getMessage()));
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<?> handleBookNotFoundException(BookNotFoundException e) {
//        return ResponseEntity.badRequest().body(ErrorDto.from(e.getMessage()));
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<?> handleAuthenticationFailedException(AuthenticationFailedException e) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorDto.from(e.getMessage()));
//    }
}
